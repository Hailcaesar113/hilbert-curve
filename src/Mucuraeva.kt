package draw

import javafx.scene.Group
import javafx.scene.paint.Color
import tornadofx.*
import tornadofx.Stylesheet.Companion.line
import kotlin.reflect.KClass

class DrawView : View () {
    private var lastX = 0.0
    private var lastY = 0.0

    private fun Hilbert(group: Group, depth: Int, dx: Float, dy: Float) {
        if (depth > 1) Hilbert(group,depth - 1, dy, dx);
        DrawRelative(group, dx, dy);
        if (depth > 1) Hilbert(group, depth - 1, dx, dy);
        DrawRelative(group, dy, dx);
        if (depth > 1) Hilbert(group,depth - 1, dx, dy);
        DrawRelative(group, -dx, -dy);
        if (depth > 1) Hilbert(group,depth - 1, -dy, -dx);
    }
    private fun DrawRelative(group: Group, dx: Float, dy: Float) {
        group.line {
            startX = lastX
            startY = lastY
            endX = lastX + dx
            endY = lastY + dy
        }
        lastX += dx;
        lastY += dy;
    }
    override val root =
            stackpane {
                group {
                    Hilbert(this, 4, 5f, 0f)
                }
            }

    class DrawViewApp : App () {
        override val primaryView = DrawView::class
    }
}
