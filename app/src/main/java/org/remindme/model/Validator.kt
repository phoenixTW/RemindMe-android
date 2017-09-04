package org.remindme.model

class Validator {
    fun isValid(title: String, date: String, time: String, switchState: Boolean): Boolean {
        var errorMessage = "Title/Date can't be empty"
        if (isValid(listOf(title, date))) {
            if (switchState && !isValid(listOf(time))) errorMessage = "Start time can't be empty" else return true
        }
        throw Exception(errorMessage)
    }

    private fun isValid(fields: List<String>): Boolean {
        val emptyFields = fields.filter { it.isEmpty() }
        return emptyFields.isEmpty()
    }
}