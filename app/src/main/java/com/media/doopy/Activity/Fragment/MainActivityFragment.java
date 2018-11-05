package com.media.doopy.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.media.doopy.Activity.MainActivity;
import com.media.doopy.Controller.Component;
import com.media.doopy.CoreData.Recipe.Hits;
import com.media.doopy.CoreData.Recipe.Recipe;
import com.media.doopy.R;

import java.util.List;
import java.util.Objects;

public class MainActivityFragment extends Fragment {
    public SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    public ContentAdapter adapter;
    private List<Component> hits;
    private MainActivity main;
    public RequestOptions options;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        refreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView = refreshLayout.findViewById(R.id.my_recycler_views);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        main.SearchRecipe(MainActivity.WATSON_DATA_RESULT);
                    }
                }
        );
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        return refreshLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main.SearchRecipe(MainActivity.WATSON_DATA_RESULT);
    }

    public void setListado(List<Component> hits) {
        this.hits = hits;
        doRefresh();
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }

    public void doRefresh(){
        adapter = new ContentAdapter(hits,MainActivityFragment.this,getActivity());
        recyclerView.removeAllViews();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
        public List<Component> hits;
        private final int LENGTH = 20;

        public ContentAdapter(List<Component> hits, MainActivityFragment context, FragmentActivity typeg) {
            this.hits=hits;
        }

        @NonNull
        @Override
        public ContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            View v = li.inflate(R.layout.card_receta, parent, false);
            return new ContentAdapter.ViewHolder(v);
        }

        @Override
        public void onViewRecycled(@NonNull ViewHolder holder) {
            super.onViewRecycled(holder);

        }

        @Override
        public void onBindViewHolder(@NonNull ContentAdapter.ViewHolder holder, int position) {
            holder.titulo.setText(((Recipe)((Hits)hits.get(position)).getRecipe()).getLabel());

            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(((Recipe)((Hits)hits.get(position)).getRecipe()).getImage())
                    .thumbnail(0.1f)
                    .apply(options)
                    .into(holder.img);

        }

        @Override
        public int getItemCount() {
            return  hits.size();
        }

        @Override
        public long getItemId(int position) {
            //return listado.get(position).get_id();
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titulo;
            private ImageView img;
            private ImageButton action;
            private View blacklayer;
            ViewHolder(View itemView) {
                super(itemView);
                titulo = (TextView) itemView.findViewById(R.id.card_title);
                img = (ImageView) itemView.findViewById(R.id.card_image);
                action = (ImageButton) itemView.findViewById(R.id.foto_eliminar);
                blacklayer = (View) itemView.findViewById(R.id.blacklayer);
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
    }
}
