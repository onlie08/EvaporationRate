package com.ch.service.dataacq;

public class DataCalConstant {

    /**
     * 获取标准大气压下液体物性参数 Ln2
     */
    public static Float[] getStardardPressInfoLn2()
    {
        return liquidLN2Param[standardPressIndex_ln2];
    }
    /**
     * 获取标准大气压下液体物性参数 LNG
     */
    public static Float[] getStardardPressInfoLNG()
    {
        return liquidLNParam[standardPressIndex_lng];
    }

    /**
     * 根据入口压力获取温度 ln2
     */
    public static Float getParamInfoLn2ByAcqPress(Float acqpress)
    {
        acqpress = acqpress/1000;
        Float maxmax = 9999.9f;
        int findIndex = -1;
        for(int i=0;i<liquidLN2Param.length;i++)
        {
            Float enviStandPress = liquidLN2Param[i][3];
            Float detPress = Math.abs(enviStandPress-acqpress);

            if(detPress < maxmax)
            {
                maxmax = detPress;
                findIndex = i;
            }
        }
        Float resK = acqpress * (liquidLN2Param[findIndex][0]/liquidLN2Param[findIndex][3]);
        return resK;
    }
    /**
     * 根据入口压力获取温度 lng
     */
    public static Float getParamInfoLngByAcqPress(Float acqpress)
    {
        acqpress = acqpress/1000;
        Float maxmax = 9999.9f;
        int findIndex = -1;
        for(int i=0;i<liquidLNParam.length;i++)
        {
            Float enviStandPress = liquidLNParam[i][3];
            Float detPress = Math.abs(enviStandPress-acqpress);

            if(detPress < maxmax)
            {
                maxmax = detPress;
                findIndex = i;
            }
        }
        Float resK = acqpress * (liquidLNParam[findIndex][0]/liquidLNParam[findIndex][3]);
        return resK;
    }

    /**
     * 根据环境压力获取汽化潜热
     */
    public static Float getParamInfoLn2ByEnviPress(Float envipress)
    {
        envipress = envipress/1000;
        Float maxmax = 9999.9f;
        int findIndex = -1;
        for(int i=0;i<liquidLN2Param.length;i++)
        {
            Float enviStandPress = liquidLN2Param[i][3];
            Float detPress = Math.abs(enviStandPress-envipress);

            if(detPress < maxmax)
            {
                maxmax = detPress;
                findIndex = i;
            }
        }

        Float resKjkg = envipress * (liquidLN2Param[findIndex][4]/liquidLN2Param[findIndex][3]);

        return resKjkg;
    }

    /**
     * 根据环境压力获取汽化潜热g
     */
    public static Float getParamInfoLngByEnviPress(Float envipress)
    {
        envipress = envipress/1000;
        Float maxmax = 9999.9f;
        int findIndex = -1;
        for(int i=0;i<liquidLNParam.length;i++)
        {
            Float enviStandPress = liquidLNParam[i][3];
            Float detPress = Math.abs(enviStandPress-envipress);

            if(detPress < maxmax)
            {
                maxmax = detPress;
                findIndex = i;
            }
        }

        Float resKjkg = envipress * (liquidLNParam[findIndex][4]/liquidLNParam[findIndex][3]);

        return resKjkg;
    }

    public static final int standardPressIndex_ln2 = 15; //LN2 标准气压对应的索引

    // 绝对温度k  摄氏度℃  密度KG/M3  压力MPA 汽化潜热 KJ/KG
    public static final Float[][] liquidLN2Param = new Float[][]{
            {63.15f,-210f,867.78f,0.01253f,215.189f},
            {64f,-209.15f,864.59f,0.014612f,214.332f},
            {65f,-208.15f,860.78f,0.017418f,213.288f},
            {66f,-207.15f,856.90f,0.020641f,212.223f},
            {67f,-206.15f,852.96f,0.024323f,211.127f},
            {68f,-205.15f,848.96f,0.028509f,210.020f},
            {69f,-204.15f,844.90f,0.033246f,208.880f},
            {70f,-203.15f,840.77f,0.038584f,207.728f},
            {71f,-202.15f,836.58f,0.044572f,206.551f},
            {72f,-201.15f,832.33f,0.051265f,205.361f},
            {73f,-200.15f,828.02f,0.058715f,204.145f},
            {74f,-199.15f,823.65f,0.066979f,202.913f},
            {75f,-198.15f,819.22f,0.076116f,201.665f},
            {76f,-197.15f,814.74f,0.086183f,200.390f},
            {77f,-196.15f,810.20f,0.097241f,199.097f},
            {77.35f,-195.8f,808.61f,0.101325f,198.643f},
            {78f,-195.15f,805.60f,0.10935f,197.786f},
            {79f,-194.15f,800.95f,0.12258f,196.445f},
            {80f,-193.15f,796.24f,0.13699f,195.085f},
            {81f,-192.15f,791.48f,0.15264f,193.703f},
            {82f,-191.15f,786.66f,0.1696f,192.290f},
            {83f,-190.15f,781.79f,0.18794f,190.855f},
            {84f,-189.15f,776.86f,0.20773f,189.386f},
            {85f,-188.15f,771.87f,0.22903f,187.894f},
            {86f,-187.15f,766.82f,0.25192f,186.367f},
            {87f,-186.15f,761.71f,0.27646f,184.804f},
            {88f,-185.15f,756.54f,0.30272f,183.205f},
            {89f,-184.15f,751.30f,0.33078f,181.570f},
            {90f,-183.15f,745.99f,0.36071f,179.896f},
            {91f,-182.15f,740.62f,0.39258f,178.184f},
            {92f,-181.15f,735.18f,0.42646f,176.427f},
            {93f,-180.15f,729.66f,0.46242f,174.626f},
            {94f,-179.15f,724.06f,0.50055f,172.778f},
            {95f,-178.15f,718.38f,0.5409f,170.881f},
            {96f,-177.15f,712.62f,0.58357f,168.935f},
            {97f,-176.15f,706.77f,0.62862f,166.930f},
            {98f,-175.15f,700.83f,0.67614f,164.869f},
            {99f,-174.15f,694.79f,0.72619f,162.750f},
            {100f,-173.15f,688.79f,0.77886f,160.567f},
            {101f,-172.15f,682.40f,0.83422f,158.317f},
            {102f,-171.15f,676.04f,0.89235f,155.997f},
            {103f,-170.15f,669.55f,0.95334f,153.604f},
            {104f,-169.15f,662.94f,1.0173f,151.132f},
            {105f,-168.15f,656.20f,1.0842f,148.576f},
            {106f,-167.15f,649.31f,1.1542f,145.931f},
            {107f,-166.15f,642.26f,1.2275f,143.191f},
            {108f,-165.15f,635.04f,1.304f,140.348f},
            {109f,-164.15f,627.64f,1.3838f,137.395f},
            {110f,-163.15f,620.04f,1.4671f,134.322f},
            {111f,-162.15f,612.21f,1.554f,131.120f},
            {112f,-161.15f,604.14f,1.6445f,127.774f},
            {113f,-160.15f,595.80f,1.7388f,124.272f},
            {114f,-159.15f,587.15f,1.8369f,120.593f},
            {115f,-158.15f,578.14f,1.939f,116.718f},
            {116f,-157.15f,568.72f,2.0452f,112.618f},
            {117f,-156.15f,558.82f,2.1555f,108.258f},
            {118f,-155.15f,548.35f,2.2703f,103.596f},
            {119f,-154.15f,537.17f,2.3895f,98.572f},
            {120f,-153.15f,525.12f,2.5133f,93.101f},
            {121f,-152.15f,511.92f,2.642f,87.064f},
            {122f,-151.15f,497.15f,2.7757f,80.273f},
            {123f,-150.15f,480.11f,2.9147f,72.405f},
            {124f,-149.15f,459.33f,3.0592f,62.821f},
            {125f,-148.15f,431.03f,3.2099f,49.867f},
            {126.20f,-146.95f,314f,3.4f,0.000f}
    };

    public static final int standardPressIndex_lng = 11; //LNG 标准气压对应的索引
    public static final Float[][] liquidLNParam = new Float[][]{
            {90.68f,-182.47f,451.23f,0.011719f,543.43f},
            {92f,-181.15f,449.520f,0.013853f,541.67f},
            {94f,-179.15f,446.90f,0.017679f,538.92f},
            {96f,-177.15f,444.26f,0.022314f,536.07f},
            {98f,-175.15f,441.59f,0.027877f,533.12f},
            {100f,-173.15f,438.89f,0.034495f,530.07f},
            {102f,-171.15f,436.15f,0.042302f,526.94f},
            {104f,-169.15f,433.39f,0.051441f,523.7f},
            {106f,-167.15f,430.59f,0.062063f,520.36f},
            {108f,-165.15f,427.76f,0.074324f,516.92f},
            {110f,-163.15f,424.89f,0.088389f,513.39f},
            {111.63f,-161.52f,422.53f,0.101325f,510.42f},
            {112f,-161.15f,422.00f,0.10443f,509.75f},
            {113f,-160.15f,420.53f,0.11324f,507.89f},
            {114f,-159.15f,419.06f,0.12261f,505.99f},
            {115f,-158.15f,417.58f,0.13257f,504.08f},
            {116f,-157.15f,416.10f,0.14313f,502.13f},
            {117f,-156.15f,414.60f,0.15432f,500.16f},
            {118f,-155.15f,413.09f,0.16616f,498.29f},
            {119f,-154.15f,411.57f,0.17867f,478.2f},
            {120f,-153.15f,410.05f,0.19189f,494.04f},
            {121f,-152.15f,408.51f,0.20583f,491.93f},
            {122f,-151.15f,406.97f,0.22052f,489.8f},
            {123f,-150.15f,405.41f,0.23599f,487.63f},
            {124f,-149.15f,403.85f,0.25225f,485.42f},
            {125f,-148.15f,402.27f,0.26933f,483.18f},
            {126f,-147.15f,400.69f,0.28727f,480.9f},
            {127f,-146.15f,399.09f,0.30607f,478.59f},
            {128f,-145.15f,397.48f,0.32578f,476.23f},
            {129f,-144.15f,395.86f,0.34641f,473.84f},
            {130f,-143.15f,394.23f,0.368f,471.4f},
            {131f,-142.15f,392.58f,0.39056f,468.92f},
            {132f,-141.15f,390.93f,0.41413f,466.41f},
            {133f,-140.15f,389.26f,0.43872f,463.84f},
            {134f,-139.15f,387.57f,0.46437f,461.24f},
            {135f,-138.15f,385.87f,0.49111f,458.58f},
            {136f,-137.15f,384.16f,0.51895f,455.87f},
            {137f,-136.15f,382.43f,0.54793f,453.12f},
            {138f,-135.15f,380.69f,0.57807f,450.32f},
            {139f,-134.15f,378.93f,0.60941f,447.46f},
            {140f,-133.15f,377.15f,0.64196f,444.55f},
            {142f,-131.15f,373.54f,0.71082f,438.55f},
            {144f,-129.15f,369.85f,0.78488f,432.33f},
            {146f,-127.15f,366.08f,0.86436f,425.84f},
            {148f,-125.15f,362.22f,0.94948f,419.09f},
            {150f,-123.15f,358.26f,1.0405f,412.04f},
            {152f,-121.15f,354.19f,1.1376f,404.68f},
            {154f,-119.15f,350.01f,1.241f,396.99f},
            {156f,-117.15f,345.69f,1.351f,388.94f},
            {158f,-115.15f,341.23f,1.4679f,380.49f},
            {160f,-113.15f,336.61f,1.5918f,371.61f},
            {162f,-111.15f,331.82f,1.723f,362.28f},
            {164f,-109.15f,326.83f,1.8618f,352.43f},
            {166f,-107.15f,321.63f,2.0085f,342.03f},
            {168f,-105.15f,316.19f,2.1633f,331f},
            {170f,-103.15f,310.47f,2.3266f,319.28f},
            {172f,-101.15f,304.45f,2.4987f,306.77f},
            {174f,-99.15f,298.06f,2.6799f,293.37f},
            {176f,-97.15f,291.26f,2.8705f,278.93f},
            {178f,-95.15f,283.95f,3.0711f,263.25f},
            {180f,-93.15f,276.00f,3.282f,246.03f},
            {182f,-91.15f,267.22f,3.5038f,226.85f},
            {184f,-89.15f,257.26f,3.737f,204.92f},
            {186f,-87.15f,245.42f,3.9825f,178.77f},
            {188f,-85.15f,230f,4.2414f,144.59f},
            {190f,-83.15f,201.510f,5.5155f,82.89f},
            {190.555f,-82.595f,162.200f,4.595f,0f}
    };
}
