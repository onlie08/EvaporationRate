package com.ch.base.http.rxjava;
import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.ch.base.base.BaseResponse;
import com.ch.evaporationrate.R;
import com.google.gson.JsonParseException;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.ch.base.http.rxjava.ApiException.BAD_NETWORK;
import static com.ch.base.http.rxjava.ApiException.CONNECT_ERROR;
import static com.ch.base.http.rxjava.ApiException.CONNECT_TIMEOUT;
import static com.ch.base.http.rxjava.ApiException.PARSE_ERROR;
import static com.ch.base.http.rxjava.ApiException.UNKNOWN_ERROR;

/**
 * @author RedLi
 * @date 2018/3/21
 */

public class ProgressSubscriber<T extends BaseResponse> implements
        ProgressCancelListener, Observer<T> {

    private static final String TAG = ProgressSubscriber.class.getSimpleName();

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;
    private boolean isShowLoading;
    private Disposable disposable;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isShowLoading) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShowLoading = isShowLoading;
        this.mProgressDialogHandler = new ProgressDialogHandler(context, this, false);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
                    .sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    public void setDialogMessage(String message) {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.setDialogMessage(message);
        }
    }

    @Override
    public void onSubscribe(Disposable s) {
        Log.d(TAG, "onSubscribe: ");
        this.disposable = s;
        if (isShowLoading) {
            showProgressDialog();
        }
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: ");
        if (((BaseResponse) t).isOk(context) && mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t.getData());
        } else {
            onFail(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError", e.getMessage());
        dismissProgressDialog();
        if (e instanceof SocketException) {
            onException(context, CONNECT_ERROR);
        } else if (e instanceof HttpException) {
            //   HTTP错误
            onException(context, BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(context, CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(context, CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(context, PARSE_ERROR);
        } else {
            onException(context, UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        Log.d(TAG, "onCancelProgress: ");
        //截断信息，下游接受不到信息
        if (!this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
        mSubscriberOnNextListener.onCancel();
    }

    /**
     * 服务器返回数据，但响应码不为200 或者 success 为false
     *
     * @param response 服务器返回的数据
     */

    private void onFail(T response) {
        if (((BaseResponse) response).getCode()!=0 && mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onFail(((BaseResponse) response).getMsg());
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    private void onException(Context context, ApiException reason) {

        switch (reason) {
            case CONNECT_ERROR:
                mSubscriberOnNextListener.onFail(context.getString(R.string.connect_error));
                break;
            case CONNECT_TIMEOUT:
                mSubscriberOnNextListener.onFail(context.getString(R.string.connect_timeout));
                break;
            case BAD_NETWORK:
                mSubscriberOnNextListener.onFail(context.getString(R.string.bad_network));
                break;
            case PARSE_ERROR:
                mSubscriberOnNextListener.onFail(context.getString(R.string.parse_error));
                break;
            case UNKNOWN_ERROR:
            default:
                mSubscriberOnNextListener.onFail(context.getString(R.string.unknown_error));
                break;
        }
    }
}
