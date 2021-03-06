package jogasa.simarro.proyectenadal.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.Image;
import android.media.MicrophoneDirection;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;



import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;

import jogasa.simarro.proyectenadal.R;
import jogasa.simarro.proyectenadal.activity.CrearPedido;
import jogasa.simarro.proyectenadal.activity.MainActivity;
import jogasa.simarro.proyectenadal.bd.MiBD;
import jogasa.simarro.proyectenadal.bd.UsuariosOperacional;
import jogasa.simarro.proyectenadal.dao.ProductDAO;
import jogasa.simarro.proyectenadal.dao.UserDAO;
import jogasa.simarro.proyectenadal.pojo.OrderProducto;
import jogasa.simarro.proyectenadal.pojo.Pedido;
import jogasa.simarro.proyectenadal.pojo.PedidoSinCompletar;
import jogasa.simarro.proyectenadal.pojo.Producto;
import jogasa.simarro.proyectenadal.pojo.Usuario;

public class FragmentComprar extends Fragment {

    private Producto producto;
    private Usuario comprador;
    int cantidad=1;
    private MiBD miBD;
    ImageView favFoto;
    public FragmentComprar(Producto producto, Usuario usuario){
        this.producto=producto;
        this.comprador=usuario;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.activity_fragment_comprar,container,false);


        miBD=MiBD.getInstance(getContext());
        TextView nombre=view.findViewById(R.id.nombreProducto);
        TextView precio=view.findViewById(R.id.precioProducto);
        TextView descripcion=view.findViewById(R.id.descripcionProducto);
        ImageView foto=view.findViewById(R.id.imagenProducto);
        Spinner cantity=view.findViewById(R.id.cantitySpinner);
        favFoto=view.findViewById(R.id.isFavBtn);

        if(producto.isFav()) favFoto.setImageResource(R.drawable.estrellafav);
        else favFoto.setImageResource(R.drawable.estrella);

        foto.setImageResource(producto.getFoto());

        final ArrayList<String> productMaxCantity=new ArrayList<String>();
        //ADD MAX VALUES TO SPINNER
        for(int i=1;i<=producto.getLimiteProducto();i++){
            productMaxCantity.add(String.valueOf(i));
        }


        final ArrayAdapter<String>adapterCantity=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,productMaxCantity);

        cantity.setAdapter(adapterCantity);

        cantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cantidad=Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nombre.setText(producto.getNombre());
        precio.setText(String.valueOf(producto.getPrecio())+"€/Kg");
        descripcion.setText(producto.getDescripcion());


        Button botonComprar=(Button)view.findViewById(R.id.comprarButton);

        botonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearPedido=new Intent(getActivity(), CrearPedido.class);
                makeOrder();
                crearPedido.putExtra("Usuario",comprador);
                startActivity(crearPedido);
            }
        });

        Button btnAddtoShipping=(Button)view.findViewById(R.id.addToShoppingCartBtn);
        btnAddtoShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addtoshipping=new Intent(getActivity(), MainActivity.class);

                makeOrder();

                addtoshipping.putExtra("Usuario",comprador);
                startActivity(addtoshipping);

            }
        });

        favFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(producto.isFav()){
                    favFoto.setImageResource(R.drawable.estrella);
                    producto.setFav(!producto.isFav());

                }
                else{
                    favFoto.setImageResource(R.drawable.estrellafav);
                    producto.setFav(!producto.isFav());

                }
            }
        });

        return view;
    }
    //ADD ORDER TO CLIENT, TO THE BD AND ADD ORDERPRODUCT TO THE MANY2MANY
    private void makeOrder(){
        Pedido sinCompletar=new Pedido(producto.getNombre());
        sinCompletar.setCantidadPedido(cantidad);

        sinCompletar.getProductos().add(producto);
        sinCompletar.setFinished(false);
        sinCompletar.setUsuarioCreador(comprador);
        comprador.getPedidos().add(sinCompletar);

        miBD.getOrderDAO().insertOrderToClient(sinCompletar,comprador);

        ArrayList<Pedido> pedidosUsuario=miBD.getOrderDAO().getPedidos(comprador);
        sinCompletar.setId(pedidosUsuario.get(pedidosUsuario.size()-1).getId());
        OrderProducto oe=new OrderProducto(sinCompletar.getId(),producto.getId());
        miBD.getOrderProductsDAO().add(oe);
    }
}