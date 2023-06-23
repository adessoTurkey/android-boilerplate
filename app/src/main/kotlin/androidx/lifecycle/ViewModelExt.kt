package androidx.lifecycle

import com.adesso.movee.base.FailureData
import com.adesso.movee.internal.util.Event
import com.adesso.movee.navigation.NavigationCommand

private const val KEY_NAVIGATION = "navigation"
private const val KEY_FAILURE_POPUP = "failure-popup"

private val ViewModel.navigationEvents: MutableLiveData<Event<NavigationCommand>>
    get() {
        return getTag(KEY_NAVIGATION) ?: setTagIfAbsent(KEY_NAVIGATION, MutableLiveData())
    }

val ViewModel.navigation: LiveData<Event<NavigationCommand>>
    get() = this.navigationEvents

private val ViewModel._failurePopup: MutableLiveData<Event<FailureData>>
    get() {
        return getTag(KEY_FAILURE_POPUP) ?: setTagIfAbsent(KEY_FAILURE_POPUP, MutableLiveData())
    }
val ViewModel.failurePopup: MutableLiveData<Event<FailureData>>
    get() = this._failurePopup

fun ViewModel.navigate(command: NavigationCommand) {
    navigationEvents.value = Event(command)
}

fun ViewModel.setFailure(data: FailureData) {
    _failurePopup.value = Event(data)
}
