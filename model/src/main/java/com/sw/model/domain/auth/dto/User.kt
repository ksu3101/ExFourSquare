package com.sw.model.domain.auth.dto

import com.sw.model.domain.common.dto.FriendsGroup
import com.sw.model.domain.common.dto.Photo

/**
 * @author burkd
 * @since 2019-11-14
 */
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: String, // ENUM (default "none")
    val relationship: String, // ENUM
    val photo: Photo,
    val friends: Friends,
    val birthday: Long,
    val tips: Tips,
    val homeCity: String,
    val contact: Contact,
    val photos: Photos,
    val type: String,   // ENUM
    val mayorShips: MayorShips,
    val checkins: CheckIns,
    val requests: Requests,
    val lists: Lists,
    val blockedStatus: String,
    val createdAt: Long,
    val referralId: String
)

data class Friends(
    val count: Int,
    val groups: List<FriendsGroup>
)

data class Tips(
    val count: Int
)

data class Contact(
    val verifiedPhone: Boolean,
    val email: String
)

data class Photos(
    val count: Int,
    val items: List<Photo>
)

data class MayorShips(
    val count: Int
    // val items: List<>
)

data class CheckIns(
    val count: Int
    // val items: List<>
)

data class Requests(
    val count: Int
)

data class Lists(
    val count: Int,
    val groups: List<ListGroups>
)

data class ListGroups(
    val type: String,
    val count: Int,
    val items: List<ListItem>
)

data class ListItem(
    val id: String,
    val name: String,
    val description: String,
    val type: String
    // 일단 필요하다고 생각 되는 것 들만
)