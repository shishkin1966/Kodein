package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISpecialistSubscriber
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.state.IStateable

interface IMessengerSubscriber : ISpecialistSubscriber, IStateable {
    fun read(message: IMessage)
}
