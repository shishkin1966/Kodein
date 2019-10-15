package shishkin.sl.kodeinpsb.sl.provider

import shishkin.sl.kodeinpsb.sl.IProviderSubscriber
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.state.IStateable

interface IMessengerSubscriber : IProviderSubscriber, IStateable {
    fun read(message: IMessage)
}
