package in.mn.life.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.mn.life.game.entity.Cell;
import in.mn.life.game.entity.SquareWorld;
import in.mn.world.R;

/**
 * Created by manuMohan on 05/06/2016.
 */

public class SquareWorldAdapter extends RecyclerView.Adapter<SquareWorldAdapter.ViewHolder> {
    private SquareWorld mSquareWorld;
    private SquareWorldAdapterListener mSquareWorldAdapterListener;

    public SquareWorldAdapter(SquareWorld squareWorld, SquareWorldAdapterListener squareWorldAdapterListener) {
        mSquareWorld = squareWorld;
        mSquareWorldAdapterListener = squareWorldAdapterListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cell, parent, false);
        return new ViewHolder(view);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cell cell = mSquareWorld.getCells().get(position);
        Context context = holder.cellImageView.getContext();
        if (cell.isAlive()) {
            holder.cellImageView.setBackgroundColor(context.getResources().getColor(R.color.cellAlive));
        } else {
            holder.cellImageView.setBackgroundColor(context.getResources().getColor(R.color.cellDead));
        }
    }

    @Override
    public int getItemCount() {
        return mSquareWorld == null ? 0 : mSquareWorld.getSize() * mSquareWorld.getSize();
    }

    public void setSquareWorld(SquareWorld mSquareWorld) {
        this.mSquareWorld = mSquareWorld;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cell)
        View cellImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cell)
        public void cellSelected() {
            Cell cell = mSquareWorld.getCells().get(getAdapterPosition());
            mSquareWorldAdapterListener.onCellSelected(cell);
        }
    }

    public interface SquareWorldAdapterListener {
        void onCellSelected(Cell cell);
    }
}
