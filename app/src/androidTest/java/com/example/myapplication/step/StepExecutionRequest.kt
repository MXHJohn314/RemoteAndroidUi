package com.example.myapplication.step

data class StepExecutionRequest(
    val methodName: String,
    val arguments: Array<out Any?>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StepExecutionRequest

        if (methodName != other.methodName) return false
        if (!arguments.contentEquals(other.arguments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = methodName.hashCode()
        result = 31 * result + arguments.contentHashCode()
        return result
    }
}
