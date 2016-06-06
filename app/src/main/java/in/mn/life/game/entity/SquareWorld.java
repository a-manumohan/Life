package in.mn.life.game.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by manuMohan on 01/06/2016.
 */

public class SquareWorld implements World, Parcelable {
    private static final int MAX_SQUARE_SIZE = 30;
    private int mSize;
    private Cell[] mWorld;//row x column

    public SquareWorld(int size) {
        if (size > MAX_SQUARE_SIZE) {
            throw new IllegalArgumentException("Square size cannot be more than " + MAX_SQUARE_SIZE);
        }
        mSize = size;
        initWorld();
    }

    private void initWorld() {
        int totalCount = mSize * mSize;
        mWorld = new Cell[totalCount];
        for (int i = 0; i < mSize; ++i) {
            for (int j = 0; j < mSize; ++j) {
                Cell cell = new Cell(i, j);
                cell.setState(Cell.State.DEAD);
                mWorld[i * mSize + j] = cell;
            }
        }
    }

    @Override
    public int getAliveNeighboursCount(Cell cell) {
        if (!isValid(cell)) {
            Log.e("ERROR", cell + "");
            throw new IllegalArgumentException("Cell is not valid");
        }
        int count = 0;
        int row = cell.getRow();
        int column = cell.getColumn();

        //top left
        if (row > 0 && column > 0) {
            if (isCellAlive(row - 1, column - 1)) {
                count++;
            }
        }

        //top
        if (row > 0) {
            if (isCellAlive(row - 1, column)) {
                count++;
            }
        }

        //top right
        if (row > 0 && column < mSize - 1) {
            if (isCellAlive(row - 1, column + 1)) {
                count++;
            }
        }

        //left
        if (column > 0) {
            if (isCellAlive(row, column - 1)) {
                count++;
            }
        }

        //right
        if (column < mSize - 1) {
            if (isCellAlive(row, column + 1)) {
                count++;
            }
        }

        //bottom left
        if (row < mSize - 1 && column > 0) {
            if (isCellAlive(row + 1, column - 1)) {
                count++;
            }
        }

        //bottom
        if (row < mSize - 1) {
            if (isCellAlive(row + 1, column)) {
                count++;
            }
        }

        //bottom right
        if (row < mSize - 1 && column < mSize - 1) {
            if (isCellAlive(row + 1, column + 1)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void setCell(Cell cell) {
        if (!isValid(cell)) {
            throw new IllegalArgumentException("Cell is not valid");
        }
        mWorld[cell.getRow() * mSize + cell.getColumn()] = cell;
    }

    public int getSize() {
        return mSize;
    }

    @Override
    public List<Cell> getCells() {
        return Arrays.asList(mWorld);
    }

    private boolean isValid(Cell cell) {
        return cell != null &&
                cell.getRow() >= 0 &&
                cell.getRow() <= mSize - 1 &&
                cell.getColumn() >= 0 &&
                cell.getColumn() <= mSize - 1;
    }

    private boolean isCellAlive(int row, int column) {
        return mWorld[row * mSize + column] != null
                && mWorld[row * mSize + column].isAlive();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mSize; ++i) {
            builder.append('\n');
            for (int j = 0; j < mSize; ++j) {
                builder.append(' ');
                if (mWorld[i * mSize + j].isAlive()) {
                    builder.append("1->");
                } else {
                    builder.append("0->");
                }
                builder.append("(");
                builder.append(i);
                builder.append(",");
                builder.append(j);
                builder.append(")");
            }
        }
        return builder.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSize);
        dest.writeTypedArray(this.mWorld, flags);
    }

    protected SquareWorld(Parcel in) {
        this.mSize = in.readInt();
        this.mWorld = in.createTypedArray(Cell.CREATOR);
    }

    public static final Creator<SquareWorld> CREATOR = new Creator<SquareWorld>() {
        @Override
        public SquareWorld createFromParcel(Parcel source) {
            return new SquareWorld(source);
        }

        @Override
        public SquareWorld[] newArray(int size) {
            return new SquareWorld[size];
        }
    };
}
