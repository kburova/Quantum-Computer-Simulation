package qcs;

import javafx.scene.canvas.Canvas;
import qcs.model.Circuit;
import qcs.model.Qubit;

import java.util.List;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.zipWithIndex;

/**
 * Created by nick on 3/22/17.
 */
public class CircuitsController {
  private final Circuit circuit;

  CircuitsController(Circuit circuit) {
    this.circuit = circuit;
  }

  private void write_qbits_to_canvas(List<Qubit> qbits, Canvas canvas, Integer size
    , Integer margin, Integer line_spacing, Integer padding_top) {
    final Stream<String> qbit_stream = qbits.stream()
      .map(qbit -> "|" + Integer.toString((int) Math.round(qbit.getState())) + ">");

    zipWithIndex(qbit_stream)
      .forEach(qbit -> {
        final Integer y = Math.round(margin + padding_top + qbit.getIndex() * size * line_spacing);
        final Integer x = 0 + margin;

        //draw qbit
        canvas.getGraphicsContext2D()
          .fillText(qbit.getValue(), x, y);

        final Integer font_width = size * 3;

        //draw line
        canvas.getGraphicsContext2D()
          .strokeLine(x + font_width, y - size / 2, canvas.getWidth(), y - size / 2);
      });
  }

  public void draw(Canvas canvas, Integer parent_width) {
    //loads of precomputing to figure out the size of this new canvas
    final Integer font_size = (int) 13.0;
    final Integer x_count = circuit.getX().getNumberOfQubits();
    final Integer y_count = circuit.getY().getNumberOfQubits();
    final Integer padding_top = font_size;
    final Integer line_spacing = 2;
    final Integer line_y = x_count * font_size * line_spacing + font_size + padding_top;

    //when quantum functions are added this will need to be adjusted to choose whichever is
    //greater parent width of internal width
    final Integer width = (parent_width > 0) ? (parent_width) : (0);

    //the 3 accounts for the line between x and y
    final Integer height = (x_count + y_count + 3) * font_size * line_spacing + padding_top;

    //erases old contents
    //there are cases where this is less efficiency, but I'll let the compiler figure that out
    canvas.setWidth(0);
    canvas.setHeight(0);
    canvas.setWidth(width);
    canvas.setHeight(height);


    //write x's
    write_qbits_to_canvas(circuit.getX().getQubits(), canvas, font_size
      , padding_top, line_spacing, font_size);

    //write horizontal line divide and labels
    final Integer x_axis = font_size / 2 - 1;
    final Integer top_y_axis = line_y - font_size + font_size / 2;
    final Integer bottom_y_axis = line_y + font_size;

    canvas.getGraphicsContext2D().fillText("x", x_axis, top_y_axis);
    canvas.getGraphicsContext2D().strokeLine(0, line_y, canvas.getWidth(), line_y);
    canvas.getGraphicsContext2D().fillText("y", x_axis, bottom_y_axis);

    //write y's
    write_qbits_to_canvas(circuit.getY().getQubits(), canvas, font_size
      , padding_top, line_spacing, line_y + font_size * 2);
  }
}
