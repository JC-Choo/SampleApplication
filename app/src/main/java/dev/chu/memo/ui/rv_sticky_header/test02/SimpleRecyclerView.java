//package dev.chu.memo.ui.rv_sticky_header.test02;
//
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import dev.chu.memo.R;
//
//public class SimpleRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickHeaderItemDecoration.StickyHeaderInterface {
//    private List<Data> mData;
//
//    public SimpleRecyclerView() {
//        mData = new ArrayList<>();
//        mData.add(new Data(1));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));`
//        mData.add(new Data(0));
//        mData.add(new Data(2));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(1));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(2));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//        mData.add(new Data(0));
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case HeaderDataImpl.HEADER_TYPE_1:
//                return new SimpleRecyclerView.HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header1_item_recycler, parent, false));
//            case HeaderDataImpl.HEADER_TYPE_2:
//                return new SimpleRecyclerView.Header2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header2_item_recycler, parent, false));
//            default:
//                return new SimpleRecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ViewHolder) {
//            ((ViewHolder) holder).bindData(position);
//        } else if (holder instanceof HeaderViewHolder){
//            ((HeaderViewHolder) holder).bindData(position);
//        } else if (holder instanceof Header2ViewHolder){
//            ((Header2ViewHolder) holder).bindData(position);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return mData.get(position).getViewType();
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    @Override
//    public int getHeaderPositionForItem(int itemPosition) {
//        int headerPosition = 0;
//        do {
//            if (this.isHeader(itemPosition)) {
//                headerPosition = itemPosition;
//                break;
//            }
//            itemPosition -= 1;
//        } while (itemPosition >= 0);
//        return headerPosition;
//    }
//
//    @Override
//    public int getHeaderLayout(int headerPosition) {
//        if (mData.get(headerPosition).getViewType() == 1)
//            return R.layout.header1_item_recycler;
//        else {
//            return R.layout.header2_item_recycler;
//        }
//    }
//
//    @Override
//    public void bindHeaderData(View header, int headerPosition) {
//
//    }
//
//    @Override
//    public boolean isHeader(int itemPosition) {
//        if (mData.get(itemPosition).getViewType() == 1 || mData.get(itemPosition).getViewType() == 2)
//            return true;
//        else
//            return false;
//    }
//
//    class HeaderViewHolder extends RecyclerView.ViewHolder {
//        TextView tvHeader;
//
//        HeaderViewHolder(View itemView) {
//            super(itemView);
//            tvHeader = itemView.findViewById(R.id.tvHeader);
//        }
//
//        void bindData(int position) {
//            tvHeader.setText(String.valueOf(position / 5));
//        }
//    }
//
//    class Header2ViewHolder extends RecyclerView.ViewHolder {
//        TextView tvHeader;
//
//        Header2ViewHolder(View itemView) {
//            super(itemView);
//            tvHeader = itemView.findViewById(R.id.tvHeader);
//        }
//
//        void bindData(int position) {
//            tvHeader.setText(String.valueOf(position / 5));
//        }
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tvRows;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            tvRows = itemView.findViewById(R.id.tvRows);
//        }
//
//        void bindData(int position) {
//            tvRows.setText("saber" + position);
//            ((ViewGroup) tvRows.getParent()).setBackgroundColor(Color.parseColor("#ffffff"));
//        }
//    }
//
//    class Data {
//        int viewType;
//
//        public Data(int viewType) {
//            this.viewType = viewType;
//        }
//
//        public int getViewType() {
//            return viewType;
//        }
//
//        public void setViewType(int viewType) {
//            this.viewType = viewType;
//        }
//    }
//}