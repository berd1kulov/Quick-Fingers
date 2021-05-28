package com.bbb.quickfingers

class HighScore {

    var id: Int? = null
    var username: String? = null
    var difficulty: Int? = null
    var time: Int? = null
    var score: Int? = null

    constructor(id: Int, username: String, difficulty: Int, time: Int, score: Int) {
        this.id = id
        this.username = username
        this.difficulty = difficulty
        this.time = time
        this.score = score
    }
}