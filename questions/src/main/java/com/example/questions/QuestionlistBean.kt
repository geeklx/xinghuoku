package com.example.questions

import com.google.gson.annotations.SerializedName

/**
 * 创建者： 霍述雷
 * 时间：  2019/1/11.
 */
class QuestionlistBean : Question {
    override fun groupName(name: String?) {
        groupName = name
    }

    private val questionKey: String? = null
    private val questionSubject: String? = null
    private val paperType: String? = null
    @SerializedName("resourceKey")
    private val audioFile: String? = null
    private val isSubtitle: String? = null
    private val description: String? = null
    private val matpKey: String? = null
    private var sort: String? = null
    private val questionInfo: QuestionInfoBean? = null
    private val totalCount: String? = null
    private val groupKey: String? = null
    private val score: String? = null
    private var groupName: String? = null
    private val isRight: String? = null
    private val userAnswer: String? = null
    private val questionSort: String? = null
    private val paperExplain: String? = null
    private val questionType: String? = null
    private val isCollected: String? = null
    private val userPracticeKey: String? = null
    private val questionlist: List<QuestionlistBean>? = null
    override fun questionKey(): String? {
        return questionKey
    }

    override fun questionSubject(): String? {
        return questionSubject
    }

    override fun paperType(): String? {
        return paperType
    }

    override fun audioFile(): String? {
        return audioFile
    }

    override fun isSubtitle(): String? {
        return isSubtitle
    }

    override fun description(): String? {
        return description
    }

    override fun matpKey(): String? {
        return matpKey
    }

    override fun sort(): String? {
        return sort
    }

    override fun sort(name: String?) {
        this.sort = name
    }

    override fun questionInfo(): QuestionInfo? {
        return questionInfo
    }

    override fun totalCount(): String? {
        return totalCount
    }

    override fun groupKey(): String? {
        return groupKey
    }

    override fun score(): String? {
        return score
    }

    override fun groupName(): String? {
        return groupName
    }

    override fun isRight(): String? {
        return isRight
    }

    override fun userAnswer(): String? {
        return userAnswer
    }

    override fun questionSort(): String? {
        return questionSort
    }

    override fun paperExplain(): String? {
        return paperExplain
    }

    override fun questionType(): String? {
        return questionType
    }

    override fun isCollected(): String? {
        return isCollected
    }

    override fun userPracticeKey(): String? {
        return userPracticeKey
    }

    override fun questionlist(): List<Question>? {
        return questionlist
    }

    class QuestionInfoBean : QuestionInfo {
        override fun isright(isright: String?) {
            this.isright = isright
        }

        @SerializedName("resourceKey")
        private val audioFile: String? = null
        private var isright: String? = null
        private val questionType: String? = null
        private val analyze: String? = null
        private val sort: String? = null
        private var useranswer: String? = null
        private var rightanswer: String? = null
        private val stem: String? = null
        private val childcount: Int = 0
        private val item: MutableList<ItemBean>? = null
        private val itemAny: ItemAnyBean? = null
        var questionKey: String? = null
        var lrcUrl: String? = null
        override fun questionKey(): String? {
            return questionKey
        }

        override fun questionKey(questionKey: String?) {
            this.questionKey = questionKey
        }
        override fun audioFile(): String? {
            return audioFile
        }

        override fun questionType(): String? {
            return questionType
        }

        override fun stem(): String? {
            return stem
        }

        override fun isright(): String? {
            return isright
        }

        override fun analyze(): String? {
            return analyze
        }

        override fun sort(): String? {
            return sort
        }

        override fun useranswer(): String? {
            return useranswer
        }

        override fun rightanswer(): String? {
            return rightanswer
        }

        override fun useranswer(useranswer: String?) {
            this.useranswer = useranswer
        }

        override fun childcount(): Int? {
            return childcount
        }

        override fun item(): MutableList<ItemBean>? {
            return item
        }

        override fun itemAny(): ItemAny? {
            return itemAny
        }

        override fun lrcUrl(): String? {
            return lrcUrl
        }

        class ItemBean : Item {
            var questionKey: String? = null
            override fun questionKey(): String? {
                return questionKey
            }

            override fun questionKey(questionKey: String?) {
                this.questionKey = questionKey
            }

            override fun analyze(analyze: String?) {
                this.analyze = analyze
            }

            override fun isright(isright: String?) {
                this.isright = isright
            }

            override fun questionType(type: String?) {
                this.questionType = type
            }

            override fun answer(): String? {
                return answer
            }


            override fun content(content: String?) {
                this.content = content
            }

            override fun order(order: String?) {
                this.order = order
            }


            private var isright: String? = null
            @SerializedName("resourceKey")
            private val audioFile: String? = null
            private var analyze: String? = null
            private val sort: String? = null
            private var useranswer: String? = null
            private val answer: String? = null
            private val rightanswer: String? = null
            private var questionType: String? = null
            private val stem: String? = null
            private val imgs: String? = null
            private var content: String? = null
            private var order: String? = null
            private val isAnswer: String? = null
            private val item: List<ItemBean>? = null

            override fun isright(): String? {
                return isright
            }

            override fun audioFile(): String? {
                return audioFile
            }

            override fun analyze(): String? {
                return analyze
            }

            override fun sort(): String? {
                return sort
            }

            override fun useranswer(): String? {
                return useranswer
            }

            override fun useranswer(useranswer: String) {
                this.useranswer = useranswer
            }

            override fun rightanswer(): String? {
                return rightanswer
            }

            override fun questionType(): String? {
                return questionType
            }

            override fun stem(): String? {
                return stem
            }

            override fun item(): List<Item>? {
                return item
            }

            override fun imgs(): String? {
                return imgs
            }

            override fun content(): String? {
                return content
            }

            override fun order(): String? {
                return order
            }

            override fun isAnswer(): String? {
                return isAnswer
            }

        }

        class ItemAnyBean : ItemAny {
            private val questionSubject: String? = null
            private val appQuestionKey: String? = null
            private val questionAnalysis: String? = null
            var questionKey: String? = null
            override fun questionKey(): String? {
                return questionKey
            }

            override fun questionKey(questionKey: String?) {
                this.questionKey = questionKey
            }
            override fun questionSubject(): String? {
                return questionSubject
            }

            override fun appQuestionKey(): String? {
                return appQuestionKey
            }

            override fun questionAnalysis(): String? {
                return questionAnalysis
            }

        }
    }

}
