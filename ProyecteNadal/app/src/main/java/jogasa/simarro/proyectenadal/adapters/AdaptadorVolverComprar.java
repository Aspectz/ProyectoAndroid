package jogasa.simarro.proyectenadal.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jogasa.simarro.proyectenadal.R;
import jogasa.simarro.proyectenadal.pojo.Pedido;

public class AdaptadorVolverComprar extends ArrayAdapter {

    Activity context;
    ArrayList<Pedido> pedidos;
    public AdaptadorVolverComprar(Fragment context, ArrayList<Pedido> pedidos){
        super(context.getActivity(), R.layout.adaptador_volver_comprar,pedidos);
        this.context=context.getActivity();
        this.pedidos=pedidos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View item=inflater.inflate(R.layout.adaptador_volver_comprar,null);

        TextView nombreProducto=(TextView) item.findViewById(R.id.nombreProducto);
        TextView fechaPedido=(TextView) item.findViewById(R.id.fechaPedido);
        ImageView imagen=(ImageView) item.findViewById(R.id.pedidoFoto);

        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        String formated=format.format(pedidos.get(position).getFechacreacionPedido().getTime());

        String fecha=getContext().getString(R.string.orderDate);

        nombreProducto.setText(pedidos.get(position).getProducto().getNombre());
        fechaPedido.setText(fecha+":"+formated);
        imagen.setImageResource(pedidos.get(position).getProducto().getFoto());

        return item;
    }
}