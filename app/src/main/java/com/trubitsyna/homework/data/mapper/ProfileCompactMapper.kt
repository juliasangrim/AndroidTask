package com.trubitsyna.homework.data.mapper

import com.trubitsyna.homework.data.local.model.ProfileCompact
import com.trubitsyna.homework.data.remote.model.ApiProfileCompact
import javax.inject.Inject

class ProfileCompactMapper @Inject constructor() {

    fun apiProfileCompactToProfileCompact(
        apiProfileCompact: ApiProfileCompact
    ): ProfileCompact {
        return ProfileCompact(
            id = apiProfileCompact.id,
            username = apiProfileCompact.username,
            displayName = apiProfileCompact.displayName,
            avatarUrl = apiProfileCompact.avatarUrl,
            subscribed = apiProfileCompact.subscribed,
        )
    }
}