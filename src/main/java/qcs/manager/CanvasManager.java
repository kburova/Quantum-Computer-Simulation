package qcs.manager;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.apache.commons.math3.complex.Complex;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.*;

import javax.swing.*;

/**
 * Created by kseniaburova on 4/9/17.
 */
public class CanvasManager {

    private Circuit circuit;
    private int xLines, yLines;
    private Pane circuitCanvas;
    private Pane xCanvas;
    private Pane yCanvas;
    private int topPadding = 40;
    private int padding = 20;
    private int gateSize = 40;
    private int beginLineX = 55;
    private int beginLineY = 36;
    /** elements for qubit canvases **/
    private int gridSplit = 64;
    private int qubitSize = 12;
    private int gap = 5;
    Color defaultColor = Color.BLACK;
    Color yellow = new Color(1, 0.83, 0.5, 1);

    private int beginQubitX = beginLineX + 20;

    public CanvasManager(Circuit circuit, Pane canvas, Pane xCanvas, Pane yCanvas){
        this.circuit = circuit;
        circuitCanvas = canvas;
        //xLines = circuit.getX().getNumberOfQubits();
        this.xCanvas = xCanvas;
        //yLines = circuit.getY().getNumberOfQubits();
        this.yCanvas = yCanvas;
    }
    public void resetCanvasManager(){

        /**  erase all previous drawing **/
        if (circuitCanvas.getChildren().size() != 0) {
            circuitCanvas.getChildren().clear();
            xCanvas.getChildren().clear();
            if (yCanvas.getChildren().size() != 0)
                yCanvas.getChildren().clear();
        }
        xLines = circuit.getX().getNumberOfQubits();
        yLines = circuit.getY().getNumberOfQubits();
    }

    /** function that draws indexes for qubits, and their values as well as 'step through' line **/
    public void drawInitState(){
        int qubitValue;
        Line step = new Line (beginLineX + 10, 25, beginLineX + 10, 15 + gateSize * (xLines+yLines) );
        step.setStroke(Color.SLATEBLUE);
        Group g = new Group();
        int i;
        for (i = 0; i < xLines; i++){
            qubitValue = circuit.getX().getQubit(i);
            //draw indexes
            Text index = new Text(padding/2, gateSize*i + topPadding, Integer.toString(i));
            index.setFont(new Font(14));
            index.setFill(Color.TEAL);

            Text qubitVal = new Text(padding/2 + 20, gateSize*i + topPadding, " |"+qubitValue+">");
            qubitVal.setFont(new Font(14));

            Line l = new Line(beginLineX, beginLineY +gateSize*i, beginLineX + 20, beginLineY +gateSize*i);
            g.getChildren().addAll(index,qubitVal,l);
        }
        drawXGrid(circuit.getX().getState());
        if (yLines != 0) {
            Line split = new Line(padding / 2, beginLineY + gateSize * xLines - gateSize / 2, beginLineX + 20, beginLineY + gateSize * xLines - gateSize / 2);
            split.setStroke(Color.TEAL);

            for (i = xLines; i < xLines + yLines; i++) {
                qubitValue = circuit.getY().getQubit(i - xLines);
                //draw indexes
                Text index = new Text(padding / 2, gateSize * i + topPadding, Integer.toString(i - xLines));
                index.setFont(new Font(14));
                index.setFill(Color.TEAL);

                Text qubitVal = new Text(padding / 2 + 20, gateSize * i + topPadding, " |"+qubitValue+">");
                qubitVal.setFont(new Font(14));

                Line l = new Line(beginLineX, beginLineY + gateSize * i, beginLineX + 20, beginLineY + gateSize * i);
                g.getChildren().addAll(index, qubitVal, l);
            }
            g.getChildren().add(split);
            drawYGrid(circuit.getY().getState());
        }
        circuitCanvas.getChildren().addAll(step, g);
    }

    /** Change initial state of registers without changing the whole circuit picture **/
    public void changeQubitVals(){
        int qubitValue;

        /** get first group that is initial state **/
        Group g = (Group) circuitCanvas.getChildren().get(1);
        for (int i = 0; i < xLines; i++){
            qubitValue = circuit.getX().getQubit(i);
            Text t = (Text) g.getChildren().get(i*3 + 1);
            t.setText("|"+qubitValue+">");
        }
        if (yLines != 0) {
            for (int i = xLines; i < xLines + yLines; i++) {
                qubitValue = circuit.getY().getQubit(i - xLines);
                Text t = (Text) g.getChildren().get(i * 3 + 1);
                t.setText("|" + qubitValue + ">");
            }

        }
        /** since initial state was changed, we move to the beginning of the circuit **/
        stepThrough(0);
    }

    /** moves 'step' line to its state and updates colors of states **/
    public void stepThrough(int step){
        int start = beginQubitX - 5;
        Line l  = (Line) circuitCanvas.getChildren().get(0);
        l.setStartX(start + step*gateSize);
        l.setEndX(start + step*gateSize);

        /** recolor amplitudes **/
        colorAmplitudes(xCanvas, circuit.getX());
        if (yLines !=0 )
            colorAmplitudes(yCanvas, circuit.getY());
    }

    public void drawXGrid(int stateX){
        drawGrid(xCanvas,xLines,stateX);
    }
    public void drawYGrid(int stateY) {
        if (yLines != 0)
            drawGrid(yCanvas,yLines,stateY);
    }

    /** draw the grid of squares for single register **/
    private void drawGrid(Pane canvas, int qubits, int initState){
        int numberOfQubits = (int) Math.pow(2, qubits);
        for (int i = 0; i <numberOfQubits; i++){
            Rectangle r = new Rectangle( (i%gridSplit+1)*gap + i%gridSplit*qubitSize, (gap + qubitSize) * (i/gridSplit + 1), qubitSize, qubitSize );
            r.setStroke(Color.GREY);
            r.setFill(defaultColor);
            if (i == initState)
                r.setFill(Color.RED);
            String binaryVal = String.format("%"+qubits+"s", Integer.toBinaryString(i)).replace(' ','0');

            Tooltip t  = new Tooltip("|"+i+"> = |"+binaryVal + ">");
            Tooltip.install(r,t);
            canvas.getChildren().add(r);
        }
    }

    public void drawUnaryOperator(int index, UnaryOperator operator){
        String name = operator.getName();
        int targetQ = operator.getTarget();
        String register = operator.getRegisterName();
        if (register.equals("Y")){
            targetQ += xLines;
        }
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

        if (yLines != 0) {
            Line split = new Line(startX, beginLineY + gateSize * xLines - gateSize / 2, startX + gateSize, beginLineY + gateSize * xLines - gateSize / 2);
            split.setStroke(Color.TEAL);
            g.getChildren().add(split);
            for (i = xLines; i < xLines + yLines; i++) {
                Line l = new Line(startX, beginLineY + gateSize * i, startX + gateSize, beginLineY + gateSize * i);
                g.getChildren().add(l);
            }
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

    public void drawBinaryOperator(int index, BinaryOperator operator) {
        String name = operator.getName();
        int targetQ = operator.getTarget();
        int controlQ = operator.getControl();
        String register = operator.getRegisterName();
        if (register.equals("Y")){
            targetQ += xLines;
            controlQ += xLines;
        }
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

        if (yLines != 0) {
            Line split = new Line(startX, beginLineY + gateSize * xLines - gateSize / 2, startX + gateSize, beginLineY + gateSize * xLines - gateSize / 2);
            split.setStroke(Color.TEAL);
            g.getChildren().add(split);
            for (i = xLines; i < xLines + yLines; i++) {
                Line l = new Line(startX, beginLineY + gateSize * i, startX + gateSize, beginLineY + gateSize * i);
                g.getChildren().add(l);
            }
        }


        Line connect = new Line( startX + 15, gateSize*targetQ + beginLineY, startX + 15,gateSize*controlQ + beginLineY );
        connect.setStroke(Color.LIGHTSALMON);
        connect.setStrokeWidth(3);
        g.getChildren().add(connect);

        if (name.equals("Swap")) {
            tag = "X";
            Text tt = new Text(startX, gateSize*targetQ + topPadding ,tag);
            tt.setWrappingWidth(30);
            tt.setTextAlignment(TextAlignment.CENTER);
            tt.setFill(Color.LIGHTSALMON);
            Text tc = new Text(startX, gateSize*controlQ + topPadding ,tag);
            tc.setWrappingWidth(30);
            tc.setTextAlignment(TextAlignment.CENTER);
            tc.setFill(Color.LIGHTSALMON);
            g.getChildren().addAll(tt,tc);
        }else{
            /** set circle/square **/
            Circle c = new Circle(startX + 15, gateSize*controlQ + beginLineY , 6);
            c.setFill(Color.LIGHTSALMON);
            g.getChildren().add(c);

            if (name.equals("CNOT")){
                //tag = "X";
                Circle r = new Circle(startX + 15, gateSize*targetQ + beginLineY , 13);
                r.setFill(Color.LIGHTSALMON);
                Line h = new Line(startX + 6, gateSize*targetQ + beginLineY , startX+30 - 6 , gateSize*targetQ + beginLineY);
                h.setStroke(Color.WHITE);
                h.setStrokeWidth(2);
                Line v = new Line(startX + 15 , gateSize*targetQ + beginLineY - 15 + 6, startX+15 , gateSize*targetQ + beginLineY + 15 - 6);
                v.setStroke(Color.WHITE);
                v.setStrokeWidth(2);
                g.getChildren().addAll(r,h,v);
            }else if (name.equals("Rotate")){
                tag = "Rx";
                Rectangle r = new Rectangle(startX, gateSize*targetQ + beginLineY - 15 , 30,30);
                r.setFill(Color.LIGHTSALMON);
                Text t = new Text(startX, gateSize*targetQ + topPadding ,tag);
                t.setWrappingWidth(30);
                t.setTextAlignment(TextAlignment.CENTER);
                g.getChildren().addAll(r,t);
            }
        }


        circuitCanvas.getChildren().add(g);
    }

    public void drawTernaryOperator(int index, ToffoliGate operator) {
        String name = operator.getName();
        int targetQ = operator.getTarget();
        int controlQ = operator.getControl1();
        int controlQ2 = operator.getControl2();

//        System.out.println(targetQ+" "+controlQ+" "+controlQ2);
        String register = operator.getRegisterName();
        if (register.equals("Y")){
            targetQ += xLines;
            controlQ += xLines;
            controlQ2 += yLines;
        }
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

        if (yLines != 0) {
            Line split = new Line(startX, beginLineY + gateSize * xLines - gateSize / 2, startX + gateSize, beginLineY + gateSize * xLines - gateSize / 2);
            split.setStroke(Color.TEAL);
            g.getChildren().add(split);
            for (i = xLines; i < xLines + yLines; i++) {
                Line l = new Line(startX, beginLineY + gateSize * i, startX + gateSize, beginLineY + gateSize * i);
                g.getChildren().add(l);
            }
        }

        /** set controls **/
        Line connect = new Line( startX + 15, gateSize*targetQ + beginLineY, startX + 15,gateSize*controlQ + beginLineY );
        connect.setStroke(Color.PLUM);
        connect.setStrokeWidth(3);
        Line connect2 = new Line( startX + 15, gateSize*targetQ + beginLineY, startX + 15,gateSize*controlQ2 + beginLineY );
        connect2.setStroke(Color.PLUM);
        connect2.setStrokeWidth(3);
        Circle c = new Circle(startX + 15, gateSize*controlQ + beginLineY , 6);
        c.setFill(Color.PLUM);
        Circle c2 = new Circle(startX + 15, gateSize*controlQ2 + beginLineY , 6);
        c2.setFill(Color.PLUM);
        g.getChildren().addAll(connect,connect2,c,c2);

     //   if (name.equals("Toffoli")) {
            Circle r = new Circle(startX + 15, gateSize * targetQ + beginLineY, 13);
            r.setFill(Color.PLUM);
            Line h = new Line(startX + 6, gateSize * targetQ + beginLineY, startX + 30 - 6, gateSize * targetQ + beginLineY);
            h.setStroke(Color.WHITE);
            h.setStrokeWidth(2);
            Line v = new Line(startX + 15, gateSize * targetQ + beginLineY - 15 + 6, startX + 15, gateSize * targetQ + beginLineY + 15 - 6);
            v.setStroke(Color.WHITE);
            v.setStrokeWidth(2);
            g.getChildren().addAll(r, h, v);
     //   }
        circuitCanvas.getChildren().add(g);
    }

    public void drawBigOperator(int index, Operator operator){
        String tag = "";
        String name = operator.getName();
        String register = operator.getRegisterName();

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
        if (yLines != 0) {
            Line split = new Line(startX, beginLineY + gateSize * xLines - gateSize / 2, startX + gateSize, beginLineY + gateSize * xLines - gateSize / 2);
            split.setStroke(Color.TEAL);
            g.getChildren().add(split);
            for (i = xLines; i < xLines + yLines; i++) {
                Line l = new Line(startX, beginLineY + gateSize * i, startX + gateSize, beginLineY + gateSize * i);
                g.getChildren().add(l);
            }
        }

        /** set rectangle of the qubit **/
        Rectangle r;
        int height;
        int textY;
        if (register.equals("X")) {
            height = gateSize * xLines - 13;
            r = new Rectangle(startX , gateSize * 0 + beginLineY - 15, 30, height);
            textY = beginLineY -8 + height/2 ;
        }else{
            height = gateSize * yLines - 13;
            r = new Rectangle(startX , gateSize * xLines + beginLineY - 15, 30, height);
            textY = gateSize * xLines -8 + beginLineY + height/2 ;
        }

        /** set text inside of qubit **/
        if (name.equals("Grover")){
            tag = "G";
            r.setFill(yellow);
        }else if (name.equals("WH")){
            tag = "WH";
            r.setFill(yellow);
        }else if (name.equals("QFT")){
            tag = "F";
            r.setFill(yellow);
        }else if (name.equals("iQFT")){
            tag = "1/F";
            r.setFill(yellow);
        }else if (name.equals("General")){
            tag = "C";
            r.setFill(yellow);
        }else if (name.equals("Eval")){
            tag = "f(x)";
            r.setFill(yellow);
        }else if (name.equals("CompB")){
            tag = "CB";
            r.setFill(Color.DEEPSKYBLUE);
        }else if (name.equals("SignB")){
            tag = "SB";
            r.setFill(Color.DEEPSKYBLUE);
        }else if (name.equals("Trash")) {
            tag = "\\_/";
            r.setFill(Color.DEEPSKYBLUE);
        }

        Text t = new Text(startX, textY , tag);
        t.setWrappingWidth(30);
        t.setTextAlignment(TextAlignment.CENTER);
        g.getChildren().addAll(r,t);
        circuitCanvas.getChildren().add(g);
    }

    /** Color amplitutes using HSB (hue, saturation, brigthness) format. Hue is and angle of the z=a+ib,
     *  and brightness is z abs length. Saturation is always 1 **/
    public void colorAmplitudes(Pane canvas, Register r) {
        double hue, saturation = 1 , brightness;

        Complex[] amp = r.getAmplitudes();
        for (int i = 0; i < amp.length; i++) {
            System.out.println(amp[i].getReal()+" "+amp[i].getImaginary());
            if (amp[i].getReal() == 0) {
                hue = Math.PI / 2;
                if (amp[i].getImaginary() < 0) {
                    hue = 1.5 * Math.PI;
                }
            } else {
                hue = Math.atan(amp[i].getImaginary() / amp[i].getReal());
                if (amp[i].getReal() < 0) {
                    hue += Math.PI;
                } else if (amp[i].getImaginary() < 0) {
                    hue += 2 * Math.PI;
                }
            }
            /** calcualate percentage of the angle, so we have have a scale form 0-1 instead of 0 - 2*Pi (0-360)**/
            brightness = Math.pow(amp[i].abs(), 0.25);
            Rectangle rec = (Rectangle) canvas.getChildren().get(i);
           // System.out.println("HS: "+hue+ " "+brightness);
            rec.setFill(Color.hsb(Math.toDegrees(hue), saturation, brightness));
        }
    }

    /** redraw all the elements of circuitCanvas **/
    public void redrawOperatorsOnly( int from ) {
        for(int i = from; i < circuit.getNumberOfOperators(); i++){
            Operator o = circuit.getOperator(i);
            String s = o.getType();
            switch (s)
            {
                case "Unary":
                    drawUnaryOperator(i, (UnaryOperator)o);
                    break;
                case "Binary":
                    drawBinaryOperator(i, (BinaryOperator)o );
                    break;
                case "Ternary":
                    drawTernaryOperator(i, (ToffoliGate)o);
                    break;
                case "Grover":
                case "Measurement":
                case "VarQbit":
                    drawBigOperator(i, o);
                    break;
                default:
                    break;
            }
        }
        stepThrough(0);
        colorAmplitudes(xCanvas,circuit.getX());
        if (yLines != 0){
            colorAmplitudes(yCanvas, circuit.getY());
        }
    }
}
