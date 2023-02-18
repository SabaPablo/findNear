package com.doce.cactus.saba.findnear.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class HomeViewModel() : ViewModel(){

    private val eventsChannel = Channel<HomeEvents>(Channel.UNLIMITED)
    val events = eventsChannel.receiveAsFlow()


}

sealed class HomeEvents {
    object ErrorHome : HomeEvents()

}