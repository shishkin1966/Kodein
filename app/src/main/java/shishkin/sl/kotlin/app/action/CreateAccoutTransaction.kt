package shishkin.sl.kotlin.app.action

import shishkin.sl.kotlin.app.data.Account
import shishkin.sl.kotlin.sl.action.IAction

class CreateAccountTransaction(val account: Account) : IAction
