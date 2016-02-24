package com.newfivefour.githubevents;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newfivefour.githubevents.databinding.EventsListItemBinding;
import com.newfivefour.githubevents.databinding.EventsListViewBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EventsList extends FrameLayout {

  private ArrayList<AppState.Event> mEvents;

  @BindingAdapter("app:events")
  public static void getEvents(EventsList v, ArrayList<AppState.Event> o) {
    v.setEvents(o);
  }

  @BindingAdapter("app:eventtime")
  public static void getEventime(TextView tv, String s) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    Date date = null;
    Date now = new Date();
    try {
      date = format.parse(s);
      int seconds = (int) Math.floor((now.getTime() - date.getTime()) / 1000);
      int interval = (int) Math.floor(seconds / 31536000);
      if (interval > 1) {
        tv.setText(interval + " years ago");
        return;
      }
      interval = (int) Math.floor(seconds / 2592000);
      if (interval > 1) {
        tv.setText(interval + " months ago");
        return;
      }
      interval = (int) Math.floor(seconds / 86400);
      if (interval > 1) {
        tv.setText(interval + " days ago");
        return;
      }
      interval = (int) Math.floor(seconds / 3600);
      if (interval > 1) {
        tv.setText(interval + " hours ago");
        tv.setTextColor(tv.getResources().getColor(android.R.color.holo_green_dark));
        return;
      }
      interval = (int) Math.floor(seconds / 60);
      if (interval > 1) {
        tv.setText(interval + " minutes ago");
        tv.setTextColor(tv.getResources().getColor(android.R.color.holo_green_dark));
        return;
      }
      tv.setTextColor(tv.getResources().getColor(android.R.color.holo_green_dark));
      tv.setText(Math.floor(seconds) + " seconds ago");
    } catch (ParseException e) {
      Log.d("HIYA", "Couldn't parse date: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @BindingAdapter("app:typestring")
  public static void getTypestring(TextView tv, String s) {
    switch (s) {
      case "PushEvent":
        tv.setText("Pushed to");
        break;
      case "IssueCommentEvent":
        tv.setText("Commented on");
        break;
      case "IssuesEvent":
        tv.setText("Created issue for");
        break;
      case "CreateEvent":
        tv.setText("Created ");
        break;
      default:
        tv.setText(s);
    }
  }

  private void setEvents(ArrayList<AppState.Event> events) {
    mEvents = events;
  }

  public EventsList(Context context) {
    super(context);
    init(null, 0);
  }

  public EventsList(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  public EventsList(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs, defStyle);
  }

  private void init(AttributeSet attrs, int defStyle) {
    EventsListViewBinding bd = DataBindingUtil.inflate(
            (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
            R.layout.events_list_view,
            this,
            true);
    bd.recView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    bd.recView.setAdapter(new EventsListAdapter(mEvents));
  }

  private class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    public EventsListAdapter(ArrayList<AppState.Event> events) {
      mEvents = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      EventsListItemBinding elvb = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.events_list_item, parent, false);
      return new ViewHolder(elvb);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.binding.setEvent(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
      return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      private EventsListItemBinding binding;

      public ViewHolder(EventsListItemBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;
      }
    }
  }
}
