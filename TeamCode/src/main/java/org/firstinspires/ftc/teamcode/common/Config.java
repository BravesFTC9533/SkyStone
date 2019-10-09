package org.firstinspires.ftc.teamcode.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

    private static final String PREFERENCES = "RobotPref";
    private SharedPreferences sp;

    public static final String POSITION = "Position";
    public static Position _position;
    public enum Position {
        BLUE_BRICKS, BLUE_BUILDINGS, RED_BRICKS, RED_BUILDINGS;

        public static Position toPosition(String position) {
            try {
                return valueOf(position);
            } catch (Exception e) {
                return BLUE_BRICKS;
            }
        }
    }
    public Position getPosition() {
       return _position;
    }
    public void setPosition(Position position) {
        this._position = position;
    }

    public Config(Context context) {
        sp = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        load();
    }

    public void save() {
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(POSITION, _position.name());

        editor.commit();
    }

    public void load() {
        _position = Position.toPosition(sp.getString(POSITION, "BLUE_BRICKS"));
    }

}