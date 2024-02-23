package aplicacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entidades.Produto;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in).useLocale(Locale.US);

		List<Produto> listaProdutos = new ArrayList<>();

		// Pedir para o usuário informar o caminho do arquivo de origem:
		System.out.println("Informe o caminho do arquivo de origem: ");
		String caminhoArquivoOrigemStr = sc.nextLine();

		// Criar o objeto File com o caminho do arquivo informado pelo usuário:
		File caminhoArquivoOrigem = new File(caminhoArquivoOrigemStr);

		// Pegar o caminho do diretório, excluindo o nome do arquivo:
		String caminhoDiretorioStr = caminhoArquivoOrigem.getParent();

		// Criar a pasta "out":
		boolean sucesso = new File(caminhoDiretorioStr + "\\out").mkdir();
		if(sucesso) {
			System.out.println("PASTA CRIADA COM SUCESSO!!!");
		}

		// Pegar o nome do caminho do arquivo de saida:
		String caminhoArquivoSaidaStr = caminhoDiretorioStr + "\\out\\resumo.csv";

		// Fazer a leitura do arquivo de origem:
		try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoOrigemStr))) {

			// Obter um item do arquivo de origem:
			String itemCsv = br.readLine();

			while (itemCsv != null) {

				// Cria um vetor de Strings para armazenar os campos nas variaveis para
				// instanciar o objeto Produto:
				String[] campos = itemCsv.split(",");

				String nome = campos[0];
				double preco = Double.parseDouble(campos[1]);
				int quantidade = Integer.parseInt(campos[2]);

				// adiciona o produto à lista:
				listaProdutos.add(new Produto(nome, preco, quantidade));

				itemCsv = br.readLine();

				// gerar o arquivo resumo.csv:
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivoSaidaStr))) {

					for (Produto p : listaProdutos) {
						bw.write(p.getNome() + ", " + String.format("%.2f", p.valorTotal()));
					}
					
					System.out.println("ARQUIVO DE SAÍDA CRIADO COM SUCESSO!!!");

				} catch (IOException e) {
					System.out.println("Erro ao grava o arquivo: " + e.getMessage());
				}

			}

		} catch (IOException e) {
			System.out.println("Erro de leitura de arquivo: " + e.getMessage());
		}

		sc.close();
	}

}
