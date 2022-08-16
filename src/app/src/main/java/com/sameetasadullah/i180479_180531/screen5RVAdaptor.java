package com.sameetasadullah.i180479_180531;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class screen5RVAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<message> messageList;
    FirebaseAuth mAuth;
    String receiverDP;
    DatabaseReference reference;

    public screen5RVAdaptor(Context context, List<message> messageList) {
        this.context = context;
        this.messageList = messageList;
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.keepSynced(true);
    }

    public String getReceiverDP() {
        return receiverDP;
    }

    public void setReceiverDP(String receiverDP) {
        this.receiverDP = receiverDP;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == R.layout.send_message_row) {
            itemView = LayoutInflater.from(context).inflate(R.layout.send_message_row, null ,false);
            return new screen5SendMessageViewHolder(itemView);
        }
        else if (viewType == R.layout.receive_message_row){
            itemView = LayoutInflater.from(context).inflate(R.layout.receive_message_row, null ,false);
            return new screen5ReceiveMessageViewHolder(itemView);
        }
        else if (viewType == R.layout.send_image_row) {
            itemView = LayoutInflater.from(context).inflate(R.layout.send_image_row, null ,false);
            return new screen5SendImageViewHolder(itemView);
        }
        else {
            itemView = LayoutInflater.from(context).inflate(R.layout.receive_image_row, null ,false);
            return new screen5ReceiveImageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof screen5SendMessageViewHolder) {
            ((screen5SendMessageViewHolder)holder).message.setText(messageList.get(position).getMessage());
            ((screen5SendMessageViewHolder)holder).time.setText(messageList.get(position).getTime());
            ((screen5SendMessageViewHolder)holder).relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Edit Message");

                    // Set up the input
                    final EditText input = new EditText(context);
                    input.setText(((screen5SendMessageViewHolder)holder).message.getText().toString());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reference.child(messageList.get(holder.getAdapterPosition()).getKey()).removeValue();
                        }
                    });
                    builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String text = input.getText().toString();
                            reference.child(messageList.get(holder.getAdapterPosition()).getKey()).child("message").setValue(text);
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                    return true;
                }
            });
        }
        else if (holder instanceof screen5ReceiveMessageViewHolder) {
            ((screen5ReceiveMessageViewHolder)holder).message.setText(messageList.get(position).getMessage());
            ((screen5ReceiveMessageViewHolder)holder).time.setText(messageList.get(position).getTime());
            Picasso.get().load(receiverDP).into(((screen5ReceiveMessageViewHolder)holder).dp);
        }
        else if (holder instanceof screen5SendImageViewHolder) {
            ((screen5SendImageViewHolder)holder).time.setText(messageList.get(position).getTime());
            ((screen5SendImageViewHolder)holder).location.setText(messageList.get(position).getLocation());
            Picasso.get().load(messageList.get(position).getImage()).into(((screen5SendImageViewHolder)holder).image);
        }
        else if (holder instanceof screen5ReceiveImageViewHolder) {
            ((screen5ReceiveImageViewHolder)holder).time.setText(messageList.get(position).getTime());
            ((screen5ReceiveImageViewHolder)holder).location.setText(messageList.get(position).getLocation());
            Picasso.get().load(messageList.get(position).getImage()).into(((screen5ReceiveImageViewHolder)holder).image);
            Picasso.get().load(receiverDP).into(((screen5ReceiveImageViewHolder)holder).dp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSenderID().equals(mAuth.getUid()) && messageList.get(position).getImage() == null) {
            return R.layout.send_message_row;
        }
        else if (messageList.get(position).getSenderID().equals(mAuth.getUid()) && messageList.get(position).getImage() != null) {
            return R.layout.send_image_row;
        }
        else if (messageList.get(position).getReceiverID().equals(mAuth.getUid()) && messageList.get(position).getImage() == null) {
            return R.layout.receive_message_row;
        }
        else {
            return R.layout.receive_image_row;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class screen5SendMessageViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView message, time;

        public screen5SendMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rl_send_message);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }
    }

    class screen5SendImageViewHolder extends RecyclerView.ViewHolder {
        TextView time, location;
        RelativeLayout relativeLayout;
        ImageView image;

        public screen5SendImageViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            relativeLayout = itemView.findViewById(R.id.rl_send_image);
            image = itemView.findViewById(R.id.image);
        }
    }

    class screen5ReceiveMessageViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView message, time;
        CircleImageView dp;

        public screen5ReceiveMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rl_receive_message);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            dp = itemView.findViewById(R.id.dp);
        }
    }

    class screen5ReceiveImageViewHolder extends RecyclerView.ViewHolder {
        TextView time, location;
        RelativeLayout relativeLayout;
        ImageView image;
        CircleImageView dp;

        public screen5ReceiveImageViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            relativeLayout = itemView.findViewById(R.id.rl_receive_image);
            image = itemView.findViewById(R.id.image);
            dp = itemView.findViewById(R.id.dp);
        }
    }
}
