/**
 * @author Diego Rochel
 */

package local.redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static Socket socket;
    private static ServerSocket server;

    private static DataInputStream in;
    private static DataOutputStream out;

    public static boolean validarCPF(String cpf) {

        //PROIBIDO VALORES COM MENOS DE 11 DÍGITOS
        if(cpf.length() != 11) {
            return false;
        }

        //BLOQUEIO DE CPF COM DIGITOS IGUAIS
        boolean todosIguais = true;

        for(int i = 1; i < cpf.length(); i++){
            if(cpf.charAt(i) != cpf.charAt(0)){
                todosIguais = false;
                break;
            }
        }

        if (todosIguais){
            return false;
        }


        //REGRA DE NEGÓCIO
        int soma = 0;
        int fator = 10;

        for(int i = 0; i < 9; i++){
            soma += Character.getNumericValue(cpf.charAt(i))*fator;
            fator--;
        }

        int modulo = soma % 11;
        int digito = (modulo < 2) ? 0:11 - modulo;

        int soma2 = 0;
        int fator2 = 11;

        for(int i = 0; i < 10; i++) {
            int num = (i < 9) ? Character.getNumericValue(cpf.charAt(i)) : digito;
            soma2 += num*fator2;
            fator2--;
        }

        modulo = soma2%11;
        int digito2 = (modulo < 2) ? 0:11 - modulo;

        int nono = Character.getNumericValue(cpf.charAt(9));
        int decimo = Character.getNumericValue(cpf.charAt(10));

        return (nono == digito && decimo == digito2);
    }


    public static void main(String[] args) {

        try {

            //VALIDANDO RECEPÇÃO DOS DADOS DO CLIENTE
            server = new ServerSocket(50000);
            socket = server.accept();

            //DEFINIÇÃO DE ENTRADAS E SAÍDAS
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            //ENTRADA DO CLIENTE
            String cpf = in.readUTF();
            System.out.println(cpf);

            boolean valido = validarCPF(cpf);

            if (valido){
                System.out.println("Este CPF é válido.");
                out.writeUTF("Este CPF é válido.");
            } else {
                System.out.println("Este CPF é inválido.");
                out.writeUTF("Este CPF é inválido.");
            }

            //FECHA O SOCKET E O SERVER
            socket.close();
            server.close();

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}