/**
 * @author Diego Rochel
 */

package local.redes;

import java.io.*;
import java.net.Socket;

public class Cliente {

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    public static void main(String[] args) {

        try{
            //ABERTURA DO SERVIDOR USADO
            socket = new Socket("127.0.0.1",50000);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            //RECEBE VALORES DO USUÁRIO
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Digite seu CPF: ");
            String cpf = br.readLine();

            //ENVIO DO VALOR AO SERVIDOR
            out.writeUTF(cpf);

            //RETORNO DO SERVIDOR
            String resultado = in.readUTF();
            System.out.println(resultado);

            socket.close();

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }


    }
}