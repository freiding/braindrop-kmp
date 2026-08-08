package by.freiding.braindrop.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.freiding.braindrop.core.ui.BrainDropTheme

enum class BrainDropButtonStyle { FILLED, OUTLINED, TEXT }

/**
 * A single button for every app state (errors, empty lists, quiz CTA, and
 * verb card): the same geometry and three fill styles, so we don't end up
 * with one-off Button/OutlinedButton with hand-rolled colors on every screen.
 */
@Composable
fun BrainDropButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: BrainDropButtonStyle = BrainDropButtonStyle.FILLED,
    height: Dp = 46.dp,
    cornerRadius: Dp = 15.dp,
    fillWidth: Boolean = false,
    textStyle: TextStyle = BrainDropTheme.type.button,
    containerColor: Color? = null,
    contentColor: Color? = null,
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
) {
    val resolvedContainer = containerColor ?: when (style) {
        BrainDropButtonStyle.FILLED -> MaterialTheme.colorScheme.primary
        BrainDropButtonStyle.OUTLINED -> MaterialTheme.colorScheme.surface
        BrainDropButtonStyle.TEXT -> Color.Transparent
    }
    val resolvedContent = contentColor ?: when (style) {
        BrainDropButtonStyle.FILLED -> Color.White
        BrainDropButtonStyle.OUTLINED -> MaterialTheme.colorScheme.primary
        BrainDropButtonStyle.TEXT -> BrainDropTheme.semantics.ink500
    }

    val sizedModifier = if (fillWidth) Modifier.fillMaxWidth() else Modifier.wrapContentWidth()
    var shaped = sizedModifier
        .height(height)
        .clip(RoundedCornerShape(cornerRadius))
        .background(resolvedContainer, RoundedCornerShape(cornerRadius))
    if (style == BrainDropButtonStyle.OUTLINED) {
        shaped = shaped.border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(cornerRadius))
    }

    Row(
        modifier = modifier
            .then(shaped)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.invoke(this)
        Text(text = text, style = textStyle, color = resolvedContent)
        trailingIcon?.invoke(this)
    }
}
