package com.mohamedrejeb.richeditor.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.isSpecified
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.RichTextValue

/**
 * Basic composable that enables users to edit rich text via hardware or software keyboard, but provides no decorations like hint or placeholder.
 * Whenever the user edits the text, [onValueChange] is called with the most up to date state represented by [RichTextValue]. [RichTextValue] contains the text entered by user, as well as selection, cursor, text composition information and rich text information. Please check [RichTextValue] for the description of its contents.
 * It is crucial that the value provided in the onValueChange is fed back into BasicRichTextEditor in order to have the final state of the text being displayed.
 *
 * BasicRichTextEditor is a wrapper around [BasicTextField] and it accepts all the parameters that [BasicTextField] accepts.
 *
 * This composable provides basic rich text editing functionality, however does not include any
 * decorations such as borders, hints/placeholder. A design system based implementation such as
 * Material Design Filled text field is typically what is needed to cover most of the needs. This
 * composable is designed to be used when a custom implementation for different design system is
 * needed.
 *
 * @param value The [RichTextValue] to be shown in the
 * [BasicRichTextEditor].
 * @param onValueChange Called when the input service updates the values in [RichTextValue].
 * @param modifier optional [Modifier] for this text field.
 * @param enabled controls the enabled state of the [BasicRichTextEditor]. When `false`, the text
 * field will be neither editable nor focusable, the input of the text field will not be selectable
 * @param readOnly controls the editable state of the [BasicRichTextEditor]. When `true`, the text
 * field can not be modified, however, a user can focus it and copy text from it. Read-only text
 * fields are usually used to display pre-filled forms that user can not edit
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines. The keyboard will be informed to not show
 * the return key as the [ImeAction]. [maxLines] and [minLines] are ignored as both are
 * automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw a cursor or selection around the text.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 * @param cursorBrush [Brush] to paint cursor with. If [SolidColor] with [Color.Unspecified]
 * provided, there will be no cursor drawn
 * @param decorationBox Composable lambda that allows to add decorations around text field, such
 * as icon, placeholder, helper messages or similar, and automatically increase the hit target area
 * of the text field. To allow you to control the placement of the inner text field relative to your
 * decorations, the text field implementation will pass in a framework-controlled composable
 * parameter "innerTextField" to the decorationBox lambda you provide. You must call
 * innerTextField exactly once.
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Deprecated(
    message = "use state instead of value",
    replaceWith = ReplaceWith(
        expression = "BasicRichTextEditor(state = , modifier = modifier, enabled = enabled, readOnly = readOnly, textStyle = textStyle, keyboardOptions = keyboardOptions, keyboardActions = keyboardActions, singleLine = singleLine, maxLines = maxLines, minLines = minLines, onTextLayout = onTextLayout, interactionSource = interactionSource, cursorBrush = cursorBrush, decorationBox = decorationBox)",
    )
)
fun BasicRichTextEditor(
    value: RichTextValue,
    onValueChange: (RichTextValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    val test = rememberPagerState(initialPage = 0, )
    test.currentPage
    BasicTextField(
        value = value.textFieldValue,
        onValueChange = {
            onValueChange(
                value.updateTextFieldValue(it)
            )
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = value.visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = decorationBox,
    )
}

/**
 * Basic composable that enables users to edit rich text via hardware or software keyboard, but provides no decorations like hint or placeholder.
 * Whenever the user edits the texe.
 *
 * BasicRichTextEditor is a wrapper around [BasicTextField] and it accepts all the parameters that [BasicTextField] accepts.
 *
 * This composable provides basic rich text editing functionality, however does not include any
 * decorations such as borders, hints/placeholder. A design system based implementation such as
 * Material Design Filled text field is typically what is needed to cover most of the needs. This
 * composable is designed to be used when a custom implementation for different design system is
 * needed.
 *
 * @param state [RichTextState] that holds the state of the [BasicRichTextEditor].
 * @param modifier optional [Modifier] for this text field.
 * @param enabled controls the enabled state of the [BasicRichTextEditor]. When `false`, the text
 * field will be neither editable nor focusable, the input of the text field will not be selectable
 * @param readOnly controls the editable state of the [BasicRichTextEditor]. When `true`, the text
 * field can not be modified, however, a user can focus it and copy text from it. Read-only text
 * fields are usually used to display pre-filled forms that user can not edit
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines. The keyboard will be informed to not show
 * the return key as the [ImeAction]. [maxLines] and [minLines] are ignored as both are
 * automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw a cursor or selection around the text.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 * @param cursorBrush [Brush] to paint cursor with. If [SolidColor] with [Color.Unspecified]
 * provided, there will be no cursor drawn
 * @param decorationBox Composable lambda that allows to add decorations around text field, such
 * as icon, placeholder, helper messages or similar, and automatically increase the hit target area
 * of the text field. To allow you to control the placement of the inner text field relative to your
 * decorations, the text field implementation will pass in a framework-controlled composable
 * parameter "innerTextField" to the decorationBox lambda you provide. You must call
 * innerTextField exactly once.
 *
 */
@Composable
fun BasicRichTextEditor(
    state: RichTextState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    BasicRichTextEditor(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = decorationBox,
        contentPadding = PaddingValues()
    )
}

/**
 * Basic composable that enables users to edit rich text via hardware or software keyboard, but provides no decorations like hint or placeholder.
 * Whenever the user edits the texe.
 *
 * BasicRichTextEditor is a wrapper around [BasicTextField] and it accepts all the parameters that [BasicTextField] accepts.
 *
 * This composable provides basic rich text editing functionality, however does not include any
 * decorations such as borders, hints/placeholder. A design system based implementation such as
 * Material Design Filled text field is typically what is needed to cover most of the needs. This
 * composable is designed to be used when a custom implementation for different design system is
 * needed.
 *
 * @param state [RichTextState] that holds the state of the [BasicRichTextEditor].
 * @param modifier optional [Modifier] for this text field.
 * @param enabled controls the enabled state of the [BasicRichTextEditor]. When `false`, the text
 * field will be neither editable nor focusable, the input of the text field will not be selectable
 * @param readOnly controls the editable state of the [BasicRichTextEditor]. When `true`, the text
 * field can not be modified, however, a user can focus it and copy text from it. Read-only text
 * fields are usually used to display pre-filled forms that user can not edit
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines. The keyboard will be informed to not show
 * the return key as the [ImeAction]. [maxLines] and [minLines] are ignored as both are
 * automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw a cursor or selection around the text.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 * @param cursorBrush [Brush] to paint cursor with. If [SolidColor] with [Color.Unspecified]
 * provided, there will be no cursor drawn
 * @param decorationBox Composable lambda that allows to add decorations around text field, such
 * as icon, placeholder, helper messages or similar, and automatically increase the hit target area
 * of the text field. To allow you to control the placement of the inner text field relative to your
 * decorations, the text field implementation will pass in a framework-controlled composable
 * parameter "innerTextField" to the decorationBox lambda you provide. You must call
 * innerTextField exactly once.
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BasicRichTextEditor(
    state: RichTextState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
    contentPadding: PaddingValues
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val localTextStyle = LocalTextStyle.current
    val clipboardManager = LocalClipboardManager.current
    // TODO: Use RichClipboardManager
    val richClipboardManager = remember {
        object : ClipboardManager {
            override fun setText(annotatedString: AnnotatedString) {
//                println("setText: $annotatedString")
//                clipboardManager.setText(AnnotatedString("Copy hhh"))
                println("setText: ${state.annotatedString.spanStyles}")
                clipboardManager.setText(state.annotatedString)
            }

            override fun getText(): AnnotatedString? {
                val annotatedString = clipboardManager.getText()
                println("getText: ${annotatedString}")
                println("getText: ${annotatedString?.text}")
                println("getText: ${annotatedString?.spanStyles}")
                return annotatedString
//                return AnnotatedString("Hi hhh")
            }
        }
    }

    CompositionLocalProvider(LocalClipboardManager provides richClipboardManager) {
        BasicTextField(
            value = state.textFieldValue,
            onValueChange = {
                state.onTextFieldValueChange(it)
            },
            modifier = modifier
                // Workaround to fix a bug in BasicTextField where it doesn't select the correct text
                // when the text contains multiple paragraphs.
                .onPointerEvent(PointerEventType.Press) {
                    val pressPosition = it.changes.firstOrNull()?.position ?: return@onPointerEvent
                    val topPadding = with(density) { contentPadding.calculateTopPadding().toPx() }
                    val startPadding = with(density) { contentPadding.calculateStartPadding(layoutDirection).toPx() }

                    var textHeight = 0f
                    var index = -1
                    val selectedParagraph = state.richParagraphList.firstOrNull { paragraph ->
                        index++
                        val lineHeight =
                            if (
                                paragraph.paragraphStyle.lineHeight.isSpecified &&
                                paragraph.paragraphStyle.lineHeight.isSp &&
                                paragraph.paragraphStyle.lineHeight.value > 0f
                            ) paragraph.paragraphStyle.lineHeight.toPx()
                            else 0f

                        val paragraphHeightSp = paragraph.getMaxFontSize()
                        val paragraphHeight =
                            if (
                                paragraphHeightSp.isSpecified &&
                                paragraphHeightSp.isSp &&
                                paragraphHeightSp.toPx() > 0f
                            ) paragraphHeightSp.toPx()
                            else localTextStyle.fontSize.toPx()

                        textHeight += paragraphHeight
                        textHeight += lineHeight

                        topPadding + textHeight > pressPosition.y
                    } ?: return@onPointerEvent

                    val nextParagraph = state.richParagraphList.getOrNull(index + 1)
                    val nextParagraphStart = nextParagraph?.children?.firstOrNull()?.textRange?.min
                    if (
                        state.selection.collapsed &&
                        state.selection.min == nextParagraphStart
                    ) {
                        state.updateTextFieldValue(
                            state.textFieldValue.copy(
                                selection = TextRange(state.selection.min - 1, state.selection.min - 1)
                            )
                        )
                    }
                },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = state.visualTransformation,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = cursorBrush,
            decorationBox = decorationBox,
        )
    }
}