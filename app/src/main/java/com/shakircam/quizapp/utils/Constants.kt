package com.shakircam.quizapp.utils

import com.shakircam.quizapp.model.NewQuestion

class Constants {
    companion object{
        const val BASE_URL = "https://api.jsonbin.io/"
    }

    object Quiz{
        fun insertKotlinQuestion(): MutableList<NewQuestion> {

            val question = mutableListOf<String>()
            question.add("Inside an extension function, what is the name of the variable that corresponds to the receiver object")
            question.add("What is the entry point for a Kotlin application?")
            question.add("You are attempting to assign an integer variable to a long variable, but Kotlin compiler flags it as an error. Why?")
            question.add("Which line of code shows how to display a nullable string's length and shows 0 instead of null?")
            question.add("Which code snippet correctly shows a for loop using a range to display \"1 2 3 4 5 6\"?")
            question.add("You are upgrading a Java class to Kotlin. What should you use to replace the Java class's static fields?")
            question.add("Your code need to try casting an object. If the cast is not possible, you do not want an exception generated, instead you want null to be assigned. Which operator can safely cast a value?")
            question.add("Which function changes the value of the element at the current iterator location?")
            question.add("Which is the proper way to declare a singleton named DatabaseManager?")
            question.add("Which is the correct declaration of an integer array with a size of 5?")


            val option = mutableListOf<String>()
            option.add("The variable is named it")
            option.add("The variable is named this")
            option.add("The variable is named receiver")
            option.add("The variable is named default")

            option.add("fun static main(){}")
            option.add("fun main(){}")
            option.add("fun Main(){}")
            option.add("public static void main(){}")

            option.add("You must wrap all implicit conversion in a try/catch block")
            option.add("You can only assign Long to an Int, not the other way around")
            option.add("There is no implicit conversion from Int to Long")
            option.add("All integers in Kotlin are of type Long")

            option.add("println(b!!.length ?: 0)")
            option.add("println(b?.length ?: 0)")
            option.add("println(b?.length ?? 0)")
            option.add("println(b == null? 0: b.length)")

            option.add("for(z in 1..7) println(\"z \")")
            option.add("for(z in 1..6) print(\"z \")")
            option.add("for(z in 1 to 6) print(\"z \")")
            option.add("for(z in 1..7) print(\"z \")")

            option.add("an anonymous object")
            option.add("a static property")
            option.add("a companion object")
            option.add("a backing field")

            option.add("as?")
            option.add("??")
            option.add("is")
            option.add("as")

            option.add("change()")
            option.add("modify()")
            option.add("set()")
            option.add("assign()")

            option.add("object DatabaseManager {}")
            option.add("singleton DatabaseManager {}")
            option.add("static class DatabaseManager {}")
            option.add("data class DatabaseManager {}")

            option.add("val arrs[5]: Int")
            option.add("val arrs = IntArray(5)")
            option.add("val arrs: Int[5]")
            option.add("val arrs = Array<Int>(5)")

            val answer = mutableListOf<String>()

            answer.add("option_two")
            answer.add("option_two")
            answer.add("option_three")
            answer.add("option_two")
            answer.add("option_two")
            answer.add("option_three")
            answer.add("option_one")
            answer.add("option_three")
            answer.add("option_one")
            answer.add("option_two")


            val list = mutableListOf<NewQuestion>()

            val item1 = NewQuestion((question[1]),(option[1]),(option[2]),(option[3]),(option[4]),(answer[1]),10)
            val item2 = NewQuestion((question[2]),(option[5]),(option[6]),(option[7]),(option[8]),(answer[2]),10)
            val item3 = NewQuestion((question[3]),(option[9]),(option[10]),(option[11]),(option[12]),(answer[3]),10)
            val item4 = NewQuestion((question[4]),(option[13]),(option[14]),(option[15]),(option[16]),(answer[4]),10)
            val item5 = NewQuestion((question[5]),(option[17]),(option[18]),(option[19]),(option[20]),(answer[5]),10)
            val item6 = NewQuestion((question[6]),(option[21]),(option[22]),(option[23]),(option[24]),(answer[6]),10)
            val item7 = NewQuestion((question[7]),(option[25]),(option[26]),(option[27]),(option[28]),(answer[7]),10)
            val item8 = NewQuestion((question[8]),(option[29]),(option[30]),(option[31]),(option[32]),(answer[8]),10)
            val item9 = NewQuestion((question[9]),(option[33]),(option[34]),(option[35]),(option[36]),(answer[9]),10)
            val item10 = NewQuestion((question[10]),(option[37]),(option[38]),(option[39]),(option[40]),(answer[10]),10)

            list.add(item1)
            list.add(item2)
            list.add(item3)
            list.add(item4)
            list.add(item5)
            list.add(item6)
            list.add(item7)
            list.add(item8)
            list.add(item9)
            list.add(item10)
            return list
        }
    }
}