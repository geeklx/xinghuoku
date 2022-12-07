package com.example.questions

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/11.
 */
interface Question {
    fun questionKey(): String?
    fun questionSubject(): String?
    fun paperType(): String?
    fun audioFile(): String?
    fun isSubtitle(): String?
    fun description(): String?
    fun matpKey(): String?
    fun sort(): String?
    fun sort(name: String?)
    fun questionInfo(): QuestionInfo?
    fun totalCount(): String?
    fun groupKey(): String?
    fun score(): String?
    fun groupName(): String?
    fun groupName(name: String?)
    fun isRight(): String?
    fun userAnswer(): String?
    fun questionSort(): String?
    fun paperExplain(): String?
    fun questionType(): String?
    fun isCollected(): String?
    fun userPracticeKey(): String?
    fun questionlist(): List<Question>?
}

interface QuestionInfo {
    fun questionKey(): String?
    fun questionKey(questionKey: String?)
    fun audioFile(): String?
    fun questionType(): String?
    fun stem(): String?
    fun isright(): String?
    fun isright(isright: String?)
    fun analyze(): String?
    fun sort(): String?
    fun useranswer(): String?
    fun rightanswer(): String?
    fun useranswer(useranswer: String?)
    fun childcount(): Int?
    fun item(): List< Item>?
    fun itemAny(): ItemAny?
    fun lrcUrl(): String?
}

interface Item {
    fun questionKey(): String?
    fun questionKey(questionKey: String?)
    fun isright(): String?
    fun isright(isright: String?)
    fun audioFile(): String?
    fun analyze(analyze: String?)
    fun analyze(): String?
    fun sort(): String?
    fun useranswer(): String?
    fun answer(): String?
    fun useranswer(useranswer: String)
    fun rightanswer(): String?
    fun questionType(): String?
    fun questionType(type: String?)
    fun stem(): String?
    fun item(): List<Item>?
    fun imgs(): String?
    fun content(): String?
    fun content(content: String?)
    fun order(): String?
    fun order(order: String?)
    fun isAnswer(): String?
}

interface ItemAny {
    fun questionKey(): String?
    fun questionKey(questionKey: String?)
    fun questionSubject(): String?
    fun appQuestionKey(): String?
    fun questionAnalysis(): String?
}
