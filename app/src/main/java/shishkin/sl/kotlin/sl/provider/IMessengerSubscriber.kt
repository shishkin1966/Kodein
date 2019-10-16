package shishkin.sl.kotlin.sl.provider

import shishkin.sl.kotlin.sl.IProviderSubscriber
import shishkin.sl.kotlin.sl.message.IMessage
import shishkin.sl.kotlin.sl.state.IStateable

interface IMessengerSubscriber : IProviderSubscriber, IStateable {
    fun read(message: IMessage)
}
