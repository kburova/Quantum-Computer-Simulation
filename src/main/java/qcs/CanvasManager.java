package qcs;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import qcs.model.Circuit;
import qcs.model.operator.*;

/**
 * Created by kseniaburova on 4/9/17.
 */
public class CanvasManager {

    private Circuit circuit;
    private int xLines, yLines = 0;
    private Pane circuitCanvas;
    private int topPadding = 40;
    private int padding = 20;
    private int gateSize = 40;
    private int beginLineX = 55;
    private int beginLineY = 36;

    private int beginQubitX = beginLineX + 20;

    public CanvasManager(Circuit circuit, Pane canvas){
        this.circuit = circuit;
        xLines = circuit.getX().getNumberOfQubits();
        if (circuit.getY() != null){
            yLines = circuit.getY().getNumberOfQubits();
        }
        yLines = circuit.getY().getNumberOfQubits();
        this.circuitCanvas = canvas;
    }

    public void drawInitState(){
        Line step = new Line (beginLineX + 10, 25, beginLineX + 10, 15 + gateSize * xLines );
        step.setStroke(Color.SLATEBLUE);
        Group g = new Group();
        int i;
        for (i = 0; i < xLines; i++){
            //draw indexes
            Text index = new Text(padding/2, gateSize*i + topPadding, Integer.toString(i));
            index.setFont(new Font(14));
            index.setFill(Color.TEAL);

            Text qubitVal = new Text(padding/2 + 20, gateSize*i + topPadding, " |0>");
            qubitVal.setFont(new Font(14));

            Line l = new Line(beginLineX, beginLineY +gateSize*i, beginLineX + 20, beginLineY +gateSize*i);
            g.getChildren().addAll(index,qubitVal,l);
        }
        circuitCanvas.getChildren().addAll(step, g);
    }

    public void changeQubitVals(){
        int qubitValue;
        /** get first group that is initial state **/
        Group g = (Group) circuitCanvas.getChildren().get(1);
        for (int i = 0; i < xLines; i++){
            qubitValue = circuit.getX().getQubit(i);
            Text t = (Text) g.getChildren().get(i*3 + 1);
            t.setText("|"+qubitValue+">");
        }
    }

    public void stepThrough(int step){
        int start = beginQubitX - 5;
        Line l  = (Line) circuitCanvas.getChildren().get(0);
        l.setStartX(start + step*gateSize);
        l.setEndX(start + step*gateSize);
    }

    public void drawOperator(int index, Operator operator){
        String name = operator.getName();
        int targetQ = operator.getTarget();
        String tag = "";
        Group g = new Group();
        int startX = beginQubitX + index * gateSize;
        int i;

        /** add index of the operator at the top**/
        Text ind = new Text(startX, 13 , Integer.toString(index));
        ind.setWrappingWidth(30);
        ind.setFill(Color.TEAL);
        ind.setTextAlignment(TextAlignment.CENTER);
        g.getChildren().add(ind);

        /** draw chunks of lines **/
        for (i = 0; i < xLines; i++){
            Line l = new Line(startX, beginLineY +gateSize*i, startX + gateSize, beginLineY +gateSize*i);
            g.getChildren().add(l);
        }
        /** set text inside of qubit **/
        if (name.equals("X")){
            tag = "X";
        }else if (name.equals("Y")){
            tag = "Y";
        }else if (name.equals("Z")){
            tag = "Z";
        }else if (name.equals("SqRoot")){
            tag = "√x";
        }else if (name.equals("Hadamard")){
            tag = "H";
        }else if (name.equals("Identity")){
            tag = "I";
        }else if (name.equals("Inverse")){
            tag = "S†";
        }else if (name.equals("Phase")){
            tag = "S";
        }else if (name.equals("Shift")) {
            tag = "T";
        }

        /** set rectangle of the qubit **/
        Rectangle r = new Rectangle(startX, gateSize*targetQ + beginLineY - 15 , 30,30);
        r.setFill(Color.DARKSEAGREEN);
        Text t = new Text(startX, gateSize*targetQ + topPadding ,tag);
        t.setWrappingWidth(30);
        t.setTextAlignment(TextAlignment.CENTER);
        g.getChildren().addAll(r,t);
        circuitCanvas.getChildren().add(g);
    }
}
