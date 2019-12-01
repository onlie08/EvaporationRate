package com.ch.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class CommonUserFliterView {
    private Context context;
    private Drawer mainDrawer = null;

    public CommonUserFliterView(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        initSlidingDrawer();
    }

    private void initSlidingDrawer() {
        mainDrawer = new DrawerBuilder()
                .withActivity((Activity) context)
                //跟drawerlayout用法相同，用这个drawer替换覆盖掉原来drawerlayout的位置
//                .withRootView(R.id.img_user)
//                .withHeader(R.layout.view_drawer_header)
                .withHeaderDivider(false)
                //启用toolbar的ActionBarDrawerToggle动画
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withDrawerLayout(R.layout.material_drawer)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("需求信息").withIcon(R.mipmap.icon_alarm).withIdentifier(1),
                        new PrimaryDrawerItem().withName("我的商品").withIcon(R.mipmap.icon_alarm).withIdentifier(2),
                        new PrimaryDrawerItem().withName("我的消息").withIcon(R.mipmap.icon_alarm).withIdentifier(3),
                        new PrimaryDrawerItem().withName("设置").withIcon(R.mipmap.icon_alarm).withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return false;
                    }
                }).build();
    }

    public void showUserFliter(){
        mainDrawer.openDrawer();
    }

    public void hideUserFliter(){
        mainDrawer.closeDrawer();
    }
}

