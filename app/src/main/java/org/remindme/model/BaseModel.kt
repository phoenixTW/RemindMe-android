package org.remindme.model

import java.util.*

interface BaseModel {
    public fun getID(): Int
    public fun getCreatedAt(): Date
}