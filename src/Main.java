import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Pessoa {
    protected String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Nome: " + nome;
    }
}

class Ator extends Pessoa {
    private ArrayList<Filme> filmesParticipados;

    public Ator(String nome) {
        super(nome);
        filmesParticipados = new ArrayList<>();
    }

    public void adicionarFilme(Filme filme) {
        filmesParticipados.add(filme);
    }

    public ArrayList<Filme> getFilmesParticipados() {
        return filmesParticipados;
    }

    @Override
    public String toString() {
        return "Ator\n" + super.toString();
    }
}

class Diretor extends Pessoa {
    private ArrayList<Filme> filmesDirigidos;

    public Diretor(String nome) {
        super(nome);
        filmesDirigidos = new ArrayList<>();
    }

    public void adicionarFilme(Filme filme) {
        filmesDirigidos.add(filme);
    }

    public ArrayList<Filme> getFilmesDirigidos() {
        return filmesDirigidos;
    }

    @Override
    public String toString() {
        return "Diretor\n" + super.toString();
    }
}

class Filme {
    private String nome;
    private int anoLancamento;
    private Diretor diretor;
    private String sinopse;
    private int estrelas;
    private ArrayList<Ator> elenco;

    public Filme(String nome, int anoLancamento, Diretor diretor, String sinopse, int estrelas) {
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.diretor = diretor;
        this.sinopse = sinopse;
        this.estrelas = estrelas;
        this.elenco = new ArrayList<>();
    }

    public void adicionarAtor(Ator ator) {
        elenco.add(ator);
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        StringBuilder elencoString = new StringBuilder();
        for (Ator ator : elenco) {
            elencoString.append(ator.nome).append(", ");
        }
        return "Nome do filme: " + nome + "\n" +
                "Ano de lançamento: " + anoLancamento + "\n" +
                "Diretor: " + diretor.nome + "\n" +
                "Sinopse: " + sinopse + "\n" +
                "Estrelas: " + estrelas + "\n" +
                "Elenco: " + elencoString.toString();
    }

    public void salvarEmArquivo() {
        String nomeArquivo = nome.replaceAll("\\s+", "_") + ".txt";
        String caminhoPasta = "arquivos_filmes/";

        try {
            File pasta = new File(caminhoPasta);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            File arquivo = new File(caminhoPasta + nomeArquivo);
            FileWriter writer = new FileWriter(arquivo);
            writer.write(this.toString());
            writer.close();
            System.out.println("Arquivo " + nomeArquivo + " criado com sucesso!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao criar o arquivo " + nomeArquivo);
            e.printStackTrace();
        }
    }
}

public class Main {
    private static ArrayList<Filme> filmes = new ArrayList<>();
    private static ArrayList<Ator> atores = new ArrayList<>();
    private static ArrayList<Diretor> diretores = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        exibirMenuPrincipal();
    }

    private static void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar novo filme");
            System.out.println("2. Ver catálogo de filmes");
            System.out.println("3. Buscar filme por nome");
            System.out.println("4. Ver Atores");
            System.out.println("5. Ver Diretores");
            System.out.println("0. Fechar aplicativo");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarFilme();
                    break;
                case 2:
                    verFilmes();
                    break;
                case 3:
                    buscarFilmePorNome();
                    break;
                case 4:
                    verAtores();
                    break;
                case 5:
                    verDiretores();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarFilme() {
        System.out.print("Nome do filme: ");
        String nomeFilme = scanner.nextLine();

        System.out.print("Ano de lançamento: ");
        int anoLancamento = scanner.nextInt();
        scanner.nextLine();

        Diretor diretorFilme = obterDiretor();

        System.out.print("Sinopse: ");
        String sinopse = scanner.nextLine();

        System.out.print("Estrelas (de 1 a 5): ");
        int estrelas = scanner.nextInt();
        scanner.nextLine();

        Filme filme = new Filme(nomeFilme, anoLancamento, diretorFilme, sinopse, estrelas);

        int opcaoAtor;
        do {
            System.out.println("Deseja adicionar um ator ao elenco? (1 - Sim, 2 - Não)");
            opcaoAtor = scanner.nextInt();
            scanner.nextLine();

            if (opcaoAtor == 1) {
                Ator ator = obterAtor();
                filme.adicionarAtor(ator);
            }
        } while (opcaoAtor != 2);

        filmes.add(filme);
        System.out.println("Filme cadastrado com sucesso!");

        System.out.println("Deseja criar um arquivo com as informações do filme? (1 - Sim, 2 - Não)");
        int opcaoArquivo = scanner.nextInt();
        scanner.nextLine();
        if (opcaoArquivo == 1) {
            filme.salvarEmArquivo();
        }
    }

    private static void verFilmes() {
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado ainda.");
        } else {
            System.out.println("Filmes cadastrados:");
            for (int i = 0; i < filmes.size(); i++) {
                System.out.println((i + 1) + ". " + filmes.get(i).getNome());
            }
            System.out.print("Digite o número do filme que deseja ver: ");
            int opcaoFilme = scanner.nextInt();
            scanner.nextLine();

            if (opcaoFilme >= 1 && opcaoFilme <= filmes.size()) {
                Filme filmeSelecionado = filmes.get(opcaoFilme - 1);
                System.out.println(filmeSelecionado);
            } else {
                System.out.println("Filme inválido. Tente novamente.");
            }
        }
    }

    private static void buscarFilmePorNome() {
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado ainda.");
            return;
        }

        scanner.nextLine();
        System.out.print("Digite o nome do filme: ");
        String nomeFilme = scanner.nextLine();

        boolean encontrado = false;
        for (Filme filme : filmes) {
            if (filme.getNome().equalsIgnoreCase(nomeFilme)) {
                System.out.println(filme);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Filme não encontrado.");
        }
    }

    private static void verAtores() {
        if (atores.isEmpty()) {
            System.out.println("Nenhum ator cadastrado ainda.");
        } else {
            System.out.println("Atores cadastrados:");
            for (int i = 0; i < atores.size(); i++) {
                System.out.println((i + 1) + ". " + atores.get(i).nome);
            }
            System.out.print("Digite o número do ator que deseja ver: ");
            int opcaoAtor = scanner.nextInt();
            scanner.nextLine();

            if (opcaoAtor >= 1 && opcaoAtor <= atores.size()) {
                Ator atorSelecionado = atores.get(opcaoAtor - 1);
                ArrayList<Filme> filmesAtor = atorSelecionado.getFilmesParticipados();
                if (filmesAtor.isEmpty()) {
                    System.out.println("Este ator não participou de nenhum filme.");
                } else {
                    System.out.println("Filmes em que " + atorSelecionado.nome + " participou:");
                    for (Filme filme : filmesAtor) {
                        System.out.println(filme.getNome());
                    }
                }
            } else {
                System.out.println("Ator inválido. Tente novamente.");
            }
        }
    }

    private static void verDiretores() {
        if (diretores.isEmpty()) {
            System.out.println("Nenhum diretor cadastrado ainda.");
        } else {
            System.out.println("Diretores cadastrados:");
            for (int i = 0; i < diretores.size(); i++) {
                System.out.println((i + 1) + ". " + diretores.get(i).nome);
            }
            System.out.print("Digite o número do diretor que deseja ver: ");
            int opcaoDiretor = scanner.nextInt();
            scanner.nextLine();

            if (opcaoDiretor >= 1 && opcaoDiretor <= diretores.size()) {
                Diretor diretorSelecionado = diretores.get(opcaoDiretor - 1);
                ArrayList<Filme> filmesDiretor = diretorSelecionado.getFilmesDirigidos();
                if (filmesDiretor.isEmpty()) {
                    System.out.println("Este diretor não dirigiu nenhum filme.");
                } else {
                    System.out.println("Filmes dirigidos por " + diretorSelecionado.nome + ":");
                    for (Filme filme : filmesDiretor) {
                        System.out.println(filme.getNome());
                    }
                }
            } else {
                System.out.println("Diretor inválido. Tente novamente.");
            }
        }
    }

    private static Diretor obterDiretor() {
        System.out.print("Nome do diretor: ");
        String nomeDiretor = scanner.nextLine();

        for (Diretor diretor : diretores) {
            if (diretor.nome.equals(nomeDiretor)) {
                return diretor;
            }
        }

        Diretor novoDiretor = new Diretor(nomeDiretor);
        diretores.add(novoDiretor);
        return novoDiretor;
    }

    private static Ator obterAtor() {
        System.out.print("Nome do ator: ");
        String nomeAtor = scanner.nextLine();

        for (Ator ator : atores) {
            if (ator.nome.equals(nomeAtor)) {
                return ator;
            }
        }

        Ator novoAtor = new Ator(nomeAtor);
        atores.add(novoAtor);
        return novoAtor;
    }
}
