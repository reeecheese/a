import java.util.Random;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;
import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class MainClass extends GraphicsProgram implements ActionListener
{

    public GOval food;

    private ArrayList<GRect> snakeBody;

    private int snakeX, snakeY, snakeWidth, snakeHeight;

    public Timer timer = new Timer(1000, this);

    private boolean isPlaying, isGameOver;
    private int score, previousScore;
    boolean instrucOn; boolean showPause;
    private Scoreboard scoreLabel;
    private GLabel instructions; private GLabel instructions2; private GLabel instructions3; private GLabel instructions4;private GLabel instructions5;private GLabel instructions6; private GLabel instructions7;



    public void run()
    {
        addKeyListeners();

        //background: snek movement zone
        GRect canvas = new GRect(50, 50, 650, 400);
        canvas.setFillColor(Color.darkGray);
        canvas.setFilled(true);

        //background: Snek death zone
        GRect theVoid = new GRect(1000, 1000);
        theVoid.setFillColor(Color.black);
        theVoid.setFilled(true);

        add(theVoid);
        add(canvas);

        //boll
        food = new Ball(0,0,20, 20) ;
        food.setFillColor(Color.yellow);
        food.setFilled(true);
        randomFood();
// snake
        snakeBody = new ArrayList<>();
        drawSnake();
        score = 0;
        setUpInfo(); instrucOn = true;

    }

    public void randomFood(){

        Random rand = new Random();

        int randX = rand.nextInt(600); int randY = rand.nextInt(350);
        food.setLocation(randX + 70, randY + 70);
        add(food);
    }

    public void setUpInfo(){
        score = 0;
        scoreLabel = new Scoreboard("Score: " + score, 100, 30);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 16)); //change displayed word font
        add(scoreLabel);

        //insturctions mass
        instructions= new GLabel("Welcome to Snek Lite!", 250, 100);
        instructions2= new GLabel("You control the snek. The Snek cannot stop moving, but can turn around.", 100, 150);
        instructions3= new GLabel("Press WASD to rotate the snake", 100, 190);
        instructions4= new GLabel("Try eating as much yellow balls as you can. ", 100, 230);
        instructions5= new GLabel("Its game over once the snek runs into itself or the edges", 100, 270);
        instructions6= new GLabel("Good luck!", 100, 310);
        instructions7= new GLabel("Press Space to START", 250, 400);
        //font
        instructions.setFont(new Font("Serif", Font.BOLD, 24));instructions2.setFont(new Font("Serif", Font.BOLD, 17));instructions3.setFont(new Font("Serif", Font.BOLD, 17));instructions4.setFont(new Font("Serif", Font.BOLD, 17));instructions5.setFont(new Font("Serif", Font.BOLD, 17));instructions6.setFont(new Font("Serif", Font.BOLD, 17));instructions7.setFont(new Font("Serif", Font.BOLD, 24));
        //color
        instructions.setColor(Color.white);instructions2.setColor(Color.white);instructions3.setColor(Color.white);instructions4.setColor(Color.white);instructions5.setColor(Color.white);instructions6.setColor(Color.white);instructions7.setColor(Color.white);
        //add
        add(instructions); add(instructions2);add(instructions3);add(instructions4);add(instructions5);add(instructions6); add(instructions7);
    }

    public void pauseScreen(){
        instructions= new GLabel("PAUSED", 300, 200);
        instructions7= new GLabel("Press Space to Unpause", 250, 400);
        instructions.setFont(new Font("Serif", Font.BOLD, 40));
        instructions7.setFont(new Font("Serif", Font.BOLD, 24));
        instructions.setColor(Color.white);instructions7.setColor(Color.white);
        add(instructions);add(instructions7);
    }

    public void removeInstructions(){
        remove(instructions); remove(instructions2); remove(instructions3); remove(instructions4); remove(instructions5); remove(instructions6); remove(instructions7);
    }


    public void drawSnake()
    {
        Random rand = new Random();
        int randX = rand.nextInt(500); int randY = rand.nextInt(350);
        for(int i = 0; i <60; i= i + 20){
            SnakePart part = new SnakePart(randX + 120 - i, randY + 80, 20, 20);
            part.setFillColor(Color.lightGray); part.setFilled(true);
            add(part);
            snakeBody.add(part);}
    }


    boolean goingUp = false;
    boolean goingLeft = false;
    boolean goingRight = true;
    boolean goingDown = false;
    public void keyPressed(KeyEvent keyPressed)
    {
        switch (keyPressed.getKeyCode())
        {
            case KeyEvent.VK_W:
                goingLeft = false; goingRight = false; goingDown = false;
                goingUp = true;
                break;

            case KeyEvent.VK_A:
                goingUp = false; goingRight = false; goingDown = false;
                goingLeft = true;
                break;

            case KeyEvent.VK_S:
                goingUp = false; goingRight = false; goingLeft = false;
                goingDown = true;
                break;

            case KeyEvent.VK_D:
                goingUp = false; goingDown = false; goingLeft = false;
                goingRight = true;
                break;

            case KeyEvent.VK_SPACE:
                if(instrucOn){
                    instrucOn = false;
                    removeInstructions();
                    timer.start();
                    try {
                        timer.wait(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else{
                    showPause = true;
                }}}

    public void touchFood(){
        if(food.getX()+20 > snakeBody.get(0).getX() && food.getX()-20 > snakeBody.get(0).getX() |
                food.getY()+20 > snakeBody.get(0).getY() && food.getY()-20 > snakeBody.get(0).getY()){
            score++;
            growSnake();}
    }

    private void redrawSnake(GRect lastLocation)
    {
        for (int i = 1; snakeBody.size()>i; i++){
            GRect newSnakeB = snakeBody.get(i);
            newSnakeB.setLocation(lastLocation.getLocation());
            lastLocation = snakeBody.get(i);
            add(newSnakeB);
            snakeBody.set(i, newSnakeB);

        }

    }

    private void growSnake()
    {
        //GRect newLast = snakeBody.get(-1);
        //newLast.move(0, -20);
        //snakeBody.add(newLast);
    }

    private void moveUp()
    {
        GRect firstPart = snakeBody.get(0);GRect lastLocation = snakeBody.get(0);
        firstPart.move(0, -20);
        snakeBody.set(0, firstPart);
        redrawSnake(lastLocation);
    }

    private void moveDown()
    {
        GRect firstPart = snakeBody.get(0); GRect lastLocation = snakeBody.get(0);
        firstPart.move(0, 20);
        snakeBody.set(0, firstPart);
        redrawSnake(lastLocation);
    }

    private void moveLeft()
    {
        GRect firstPart = snakeBody.get(0);GRect lastLocation = snakeBody.get(0);
        firstPart.move(-20, 0);
        snakeBody.set(0, firstPart);
        redrawSnake(lastLocation);
    }

    private void moveRight()
    {
        GRect firstPart = snakeBody.get(0);GRect lastLocation = snakeBody.get(0);
        firstPart.move(20, 0);
        snakeBody.set(0, firstPart);
        redrawSnake(lastLocation);
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(goingUp){
            moveUp();
        }
        else if(goingLeft){
            moveLeft();
        }
        else if(goingDown){
            moveDown();
        }
        else if(goingRight){
            moveRight();
        }
        touchFood();
        if(showPause){
            timer.stop();
            instrucOn = true;
            pauseScreen();
            }
        }


    public static void main(String[] args)
    {
        new MainClass().start();
    }
}