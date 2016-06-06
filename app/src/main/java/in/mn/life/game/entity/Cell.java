package in.mn.life.game.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manuMohan on 01/06/2016.
 */

public class Cell implements Parcelable {
    private int mRow;
    private int mColumn;

    public Cell(int row, int column) {
        mRow = row;
        mColumn = column;
        mCurrentState = State.DEAD;
    }

    public enum State {
        ALIVE,
        DEAD
    }

    private State mCurrentState;

    public State getState() {
        return mCurrentState;
    }

    public void setState(State mCurrentState) {
        this.mCurrentState = mCurrentState;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(int mRow) {
        this.mRow = mRow;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int mColumn) {
        this.mColumn = mColumn;
    }

    public boolean isAlive() {
        return mCurrentState == State.ALIVE;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRow);
        dest.writeInt(this.mColumn);
        dest.writeInt(this.mCurrentState == null ? -1 : this.mCurrentState.ordinal());
    }

    protected Cell(Parcel in) {
        this.mRow = in.readInt();
        this.mColumn = in.readInt();
        int tmpMCurrentState = in.readInt();
        this.mCurrentState = tmpMCurrentState == -1 ? null : Cell.State.values()[tmpMCurrentState];
    }

    public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel source) {
            return new Cell(source);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };
}
