package Bibi_ElGareh_Team;

import EDU.gatech.cc.is.abstractrobot.SocSmall;
import EDU.gatech.cc.is.util.Vec2;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lounis Bibi et Boris El Gareh
 * @version 1.0
 * Classe GameContext qui décrit toutes les informations relatives au jeu : vecteurs, attraction-repultion...
 */
public class GameContext {

    public final static double X_LIMIT = 1.24;
    public final static double Y_LIMIT = 0.7625;
    public static final int LOCK = 300;

    private SocSmall abstract_robot;

    private int numPlayer;
    private long curr_time;
    private Vec2 axe;
    private Vec2 position;
    private Vec2 ball;
    private Vec2 ourGoal;
    private Vec2 opponentGoal;
    private int countLocked;
    private int side;

    private Vec2 ballPosition;

    public final static int N_ATTACKERS=3;
    public final static int N_DEFENDERS=1;
    public final static int N_OFFSIDE=1;

    /**
     * Constructeur de la classe GameContext
     * @param abstract_robot L'agent joueur
     */
    public GameContext(SocSmall abstract_robot) {

        this.abstract_robot = abstract_robot;
        this.countLocked = 0;
    } 

    /**
     * Méthode qui initialise le jeu. Elle est appelée à chaque itération de TakeStep()
     */
    public void init() {
        this.curr_time = this.abstract_robot.getTime();
        this.position= this.abstract_robot.getPosition(curr_time);
        this.ball= this.abstract_robot.getBall(curr_time);
        this.ourGoal = this.abstract_robot.getOurGoal(curr_time);
        this.opponentGoal = this.abstract_robot.getOpponentsGoal(curr_time);

        if(this.ourGoal.x < 0)
            this.side = -1;
        else
            this.side = 1;
    }

    public boolean ballIsInFront() {
        double scalarProduct = this.ball.x * this.ourGoal.x + this.ball.y* this.ourGoal.y;
        if (scalarProduct < 0){
            // la balle est devant le joueur
            return true;
        }
        // la balle est derriere le joueur
        return false;
    }


    public boolean emptyPlayground(Vec2 axis, double interval){

        for (Vec2 ennemy : this.getOpponentsTeamPlayers()) {
            if (Utilities.inAxis(axis.t, ennemy , interval)) {
                return false;
            }
        }
        return true;
    }

    public boolean teammateCloserToTheBall(){
        for (Vec2 teammate : this.abstract_robot.getTeammates(this.curr_time)){
            if (Utilities.getDistance(this.ball, teammate).r < this.ball.r) {
                return true;
            }
        }
        return false;
    }

    public boolean teammateCloserToOurGoal(){
        for (Vec2 teammate : this.abstract_robot.getTeammates(this.curr_time)){
            if (Utilities.getDistance(this.ourGoal, teammate).r < this.ourGoal.r) {
                return true;
            }
        }
        return false;
    }

    public void countLocked() {
        if (!this.isLocked()) {
            countLocked = 0;
        }
        else {
            countLocked++;
        }
        this.ballPosition = Utilities.getAbsolutePosition(this.ball, this.position);
    }

    public Boolean isLocked(){
        if(this.ballPosition == null){
            return false;
        }
        Vec2 anciennePos = this.ballPosition;
        Vec2 nouvellePos = Utilities.getAbsolutePosition(this.ball, this.position);

        return  Utilities.equalsPosition(anciennePos, nouvellePos, 0.04);

    }

    /******** Accesseurs  ********/

    /**
     * @return the abstract_robot
     */
    public SocSmall getAbstractRobot() {
        return abstract_robot;
    }

    /**
     * @return the ball
     */
    public Vec2 getBall() {
        return ball;
    }

    /**
     * @return the curr_time
     */
    public long getCurrTime() {
        return curr_time;
    }
    
    /**
     * @return the numPlayer
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    public Vec2[] getTeamMates() { return abstract_robot.getTeammates(curr_time); }

    public Vec2[] getOpponentsTeamPlayers() {
        return abstract_robot.getOpponents(curr_time);
    }

    /**
     * @return the opponentGoal
     */
    public Vec2 getOpponentGoal() {
        return opponentGoal;
    }

    /**
     * @return the ourGoal
     */
    public Vec2 getOurGoal() {
        return ourGoal;
    }

    /**
     * @return the position
     */
    public Vec2 getPosition() {
        return position;
    }

    public int getSide() {return this.side;}

    public int getCountLocked() {return this.countLocked;}

    public void setCountLocked(int value) {this.countLocked = value;}
}