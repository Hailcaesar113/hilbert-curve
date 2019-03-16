package draw


import javafx.beans.property.StringProperty
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*
import tornadofx.Stylesheet.Companion.line
import kotlin.reflect.KClass
import javafx.scene.shape.Line;

class DrawView : View () {
    private var lastX = 0.0
    private var lastY = 0.0
    private var depth = 0

    private fun Hilbert(group: Group, depth: Int, dx: Float, dy: Float) {
        if (depth > 1) Hilbert(group,depth - 1, dy, dx);
        DrawRelative(group, dx, dy, Color.BLUE);
        if (depth > 1) Hilbert(group, depth - 1, dx, dy);
        DrawRelative(group, dy, dx, Color.RED);
        if (depth > 1) Hilbert(group,depth - 1, dx, dy);
        DrawRelative(group, -dx, -dy, Color.GREEN);
        if (depth > 1) Hilbert(group,depth - 1, -dy, -dx);
    }
    private fun DrawRelative(group: Group, dx: Float, dy: Float, color: Color) {
        group.line {
            startX = lastX
            startY = lastY
            endX = lastX + dx
            endY = lastY + dy
            stroke = color
        }
        lastX += dx;
        lastY += dy;
    }
    override val root =
            hbox {
                var groupp: Group by singleAssign()
                vbox {
                    var number: TextField by singleAssign()
                    label("Depth")
                    number = textfield()
                    button("Go") {
                        action {
                            depth = number.text.toInt()
                            Hilbert(groupp, depth, 10f, 0f)
                        }
                    }
                }
                stackpane {
                    groupp = group {

                    }
                }
            }

}

class DrawViewApp : App () {
    override val primaryView = DrawView::class
}
