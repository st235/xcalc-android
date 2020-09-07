package st235.com.github.calculator.data

import androidx.core.text.HtmlCompat
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import st235.com.github.calculator.data.storage.KeyValueStorage
import st235.com.github.calculator.startup.TokenService
import st235.com.github.calculator.presentation.calculator.keyboard.widget.WidgetButton
import st235.com.github.calculator_core.utils.TokenHelper
import javax.inject.Inject

class WidgetsManager @Inject constructor(
    private val keyValueStorage: KeyValueStorage,
    private val tokenService: TokenService,
    private val themesManager: ThemesManager
) {

    private val widgetsSubject = BehaviorSubject.create<List<WidgetButton>>()

    fun observeActiveWidgets(): Observable<List<WidgetButton>> {
        return widgetsSubject.startWith(createWidgets())
    }

    fun replaceWidget(oldId: String, newId: String) {
        val widgets = createWidgets().toMutableList()
        widgets[widgets.indexOfFirst { it.id == oldId }] = createWidget(newId)
        keyValueStorage[KEYS_WIDGET] = widgets.map { it.id }
        widgetsSubject.onNext(widgets)
    }

    private fun createWidgets(): List<WidgetButton> {
        val widgets = mutableListOf<WidgetButton>()

        for (widget in keyValueStorage.getStringList(KEYS_WIDGET, DEFAULT_WIDGETS)) {
            widgets.add(createWidget(widget))
        }

        return widgets
    }

    private fun createWidget(id: String): WidgetButton {
        return WidgetButton(
            id = id,
            token = HtmlCompat.fromHtml(tokenService.findTokenById(id).shortValue, HtmlCompat.FROM_HTML_MODE_COMPACT),
            themeNode = themesManager.getActiveTheme().primary
        )
    }

    private companion object {
        val KEYS_WIDGET = "keys.widget"

        val DEFAULT_WIDGETS = listOf(
            TokenHelper.ID_FUN_SIN,
            TokenHelper.ID_FUN_COS,
            TokenHelper.ID_CONST_PI,
            TokenHelper.ID_OP_POWER
        )
    }
}