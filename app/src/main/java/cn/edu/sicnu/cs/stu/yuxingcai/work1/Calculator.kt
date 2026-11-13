package cn.edu.sicnu.cs.stu.yuxingcai.work1

/**
 * 计算类：实现两个数的加、减、乘、除运算。
 */
class Calculator {

    fun add(a: Double, b: Double): Double = a + b

    fun subtract(a: Double, b: Double): Double = a - b

    fun multiply(a: Double, b: Double): Double = a * b

    fun divide(a: Double, b: Double): Double {
        if (b == 0.0) {
            throw ArithmeticException("除数不能为0")
        }
        return a / b
    }

    /**
     * 根据运算符对两个数执行运算。
     *
     * @param a        第一个数
     * @param operator 运算符，取值为 +、-、*、/
     * @param b        第二个数
     * @return 运算结果
     */
    fun calculate(a: Double, operator: String, b: Double): Double {
        return when (operator) {
            "+" -> add(a, b)
            "-" -> subtract(a, b)
            "*" -> multiply(a, b)
            "/" -> divide(a, b)
            else -> throw IllegalArgumentException("未知运算符：$operator")
        }
    }
}
