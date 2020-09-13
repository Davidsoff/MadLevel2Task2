package nl.soffware.madlevel2task2

class Question(
    var question: String,
    var answer: Boolean,
    var explanation: String
) {


    companion object {
        val QUESTIONS = arrayOf(
            "A 'val' and 'var' are the same,",
            "Mobile Application Development grants 12 EC.",
            "A Unit in Kotlin corresponds to a void in Java.",
            "In Kotlin ' When' replaces the 'switch' operator in Java."
        )
        val answers = arrayOf(
            false,
            false,
            true,
            true
        )
        val explanations = arrayOf(
            "",
            "",
            "",
            ""
        )
    }
}