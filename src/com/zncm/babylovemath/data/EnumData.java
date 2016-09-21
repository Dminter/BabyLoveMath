package com.zncm.babylovemath.data;

public class EnumData {

    public enum MathTypeEnum {


        TEST_SCAN(-1, "试题浏览"), ADD(0, "加法测试"), SUBTRACTION(1, "减法测试"), MULTIPLICATION(2, "乘法测试"), DIV(3, "除法测试"),


        ADD_SUBTRACTION(4, "加减测试"), MULTIPLICATION_DIV(5, "乘除测试"), ALL(6, "四则运算测试"),


        ALIPAY(7, "如果喜欢,可以捐助我们~\n(支付宝-https://me.alipay.com/dminter)");
        private int value;
        private String strName;

        private MathTypeEnum(int value, String strName) {
            this.value = value;
            this.strName = strName;
        }

        public int getValue() {
            return value;
        }

        public String getStrName() {
            return strName;
        }

        public static MathTypeEnum valueOf(int value) {
            for (MathTypeEnum mathTypeEnum : MathTypeEnum.values()) {
                if (mathTypeEnum.getValue() == value) {
                    return mathTypeEnum;
                }
            }
            return null;
        }


    }


}
