package in.mn.life.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import in.mn.life.adapter.SquareWorldAdapter;
import in.mn.life.game.entity.Cell;
import in.mn.life.game.entity.SquareWorld;
import in.mn.world.R;

public class SquareWorldFragment extends BaseFragment {
    private static final String ARG_SIZE = "arg_size";
    private OnFragmentInteractionListener mListener;
    private SquareWorldAdapter mSquareWorldAdapter;
    private SquareWorld mSquareWorld;

    private int mSize;

    @BindView(R.id.world)
    RecyclerView mWorldRecyclerView;

    public SquareWorldFragment() {
        // Required empty public constructor
    }


    public static SquareWorldFragment newInstance(int size) {
        SquareWorldFragment squareWorldFragment = new SquareWorldFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SIZE, size);
        squareWorldFragment.setArguments(args);
        return squareWorldFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_square_world, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mSize = mListener.getWorldSize();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setSquareWorld(SquareWorld squareWorld) {
        mSquareWorld = squareWorld;
        updateViews();
    }


    private void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mSize);
        mWorldRecyclerView.setLayoutManager(gridLayoutManager);
        mSquareWorldAdapter = new SquareWorldAdapter(mSquareWorld, cell -> mListener.onCellSelected(cell));
        mWorldRecyclerView.setAdapter(mSquareWorldAdapter);
    }

    private void updateViews() {
        mSquareWorldAdapter.setSquareWorld(mSquareWorld);
        mSquareWorldAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void onCellSelected(Cell cell);
        int getWorldSize();
    }
}
