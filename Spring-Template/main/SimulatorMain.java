package mx.omnitracs.maya.lm.simulator.main;

//import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mx.omnitracs.maya.lm.commons.CommandsType;
import mx.omnitracs.maya.lm.simulator.CommandsInterpreter;
import mx.omnitracs.maya.thread.OperationExecuted;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @see Clase principal para levantar el connector en modo standalone
 * 
 * 
 * @author montieln
 * @version 1.0
 */
public class SimulatorMain {

	public static Logger        logger = LoggerFactory.getLogger(SimulatorMain.class);
	private CommandsInterpreter command;

	public SimulatorMain() {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "protocol-model-context.xml", "adapter-conector-client-context.xml", "server-device-context.xml",
		        "simulator-context.xml" });
		command = context.getBean("commandsInterpreter", CommandsInterpreter.class);
	}

	public static void main(String args[]) throws FileNotFoundException, InterruptedException, ExecutionException {

		if (args.length == 0) {
			logger.info("Incorrect invcation");
			logger.info("Options allowed");
			logger.info("java -jar  simulator-0.0.1-SNAPSHOT.jar console");
			logger.info("Or");
			logger.info("java -jar  simulator-0.0.1-SNAPSHOT.jar file /my/path/to/myFile");
		}

		SimulatorMain connector = new SimulatorMain();
		connector.readConsoleThreads();
/*
		if (args[0].equals("console"))
			connector.readConsole();
		else if (args[0].equals("file"))
			connector.readConsoleFile(args[1]);
		else if (args[0].equals("multiTrhead"))
			connector.readConsoleThreads();
		else {
			logger.info("Options allowed (console || file)");
			logger.info("example program console or program file path_file");
		}*/

	}

	/**
	 * @see metodo para leer un archivo y enviarlo
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	private void readConsoleFile(String filePath) throws FileNotFoundException {

		String lineCmd = null;

		Scanner scanner = new Scanner(new File(filePath));

		while (((lineCmd = scanner.nextLine()) != null)) {

			String[] readLine = lineCmd.split(",");
			String commandMsg = readLine[0].trim();
			String message = readLine[1].trim();

			command.sendMessage(commandMsg, message);

		}
	}

	/**
	 * @see Metodo para leer de una consola
	 * @throws FileNotFoundException
	 */
	public void readConsole() throws FileNotFoundException {

		StringBuffer legend = new StringBuffer();
		legend.append("\nUse:");
		
		legend.append("\n //////////////////// Formato User Message //////////////////");
		legend.append("\n Formato Definicion Macros: macroType*macroId*macroVersion*macroMandatoryText*macroLabelMessage*macroTextSize");
		legend.append("\n Formato USO Macros: macroId*macroVersion*textMessage");
		legend.append("\n Formato Texto Plano : textMessage \n");
		legend.append("\n").append(CommandsType.COMMAND4);
		legend.append(", DeviceModel|MessageId|SerialNumber|UserMessage|UserMessageType|UserMessageModifier, NumeroHilos, NumeroDeProcesosPorHilo");
		
		legend.append("\n").append(CommandsType.COMMAND6);
		legend.append(", DeviceModel|MessageId|ScriptConf|SerialNumber|ParameterID|ParameterValue");
		legend.append("\n").append(CommandsType.COMMAND7);
		legend.append(", DeviceModel|MessageId|SerialNumber|ParameterID|ParameterValue|estadoInicial");
		legend.append("\n").append(CommandsType.COMMAND7TO3);
		legend.append(", DeviceModel|MessageId|SerialNumber|ParameterID|ParameterValue|estadoInicial");
		legend.append("\n").append(CommandsType.LMSERVER);
		legend.append(", HexadecimalValue|Host");

		Scanner scanner = new Scanner(new InputStreamReader(System.in));

		logger.info(legend.toString());

		String lineCmd = null;

		while (((lineCmd = scanner.nextLine()) != null)) {

			logger.info(legend.toString());

			String[] readLine = lineCmd.split(",");

			String commandMsg = readLine[0].trim();
			String message = readLine[1].trim();

			command.sendMessage(commandMsg, message);

		}

	}

	/**
	 * @see Metodo para leer de una consola y ejecutar multiThread
	 * @throws FileNotFoundException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void readConsoleThreads() throws InterruptedException, ExecutionException {

		StringBuffer legend = new StringBuffer();
		legend.append("\nUse:");
		
		legend.append("\n //////////////////// Formato User Message //////////////////");
		legend.append("\n Formato Definicion Macros: macroType*macroId*macroVersion*macroMandatoryText*macroLabelMessage*macroTextSize");
		legend.append("\n Formato USO Macros: macroId*macroVersion*textMessage");
		legend.append("\n Formato Texto Plano : textMessage \n");
		
		legend.append("\n").append(CommandsType.COMMAND4);		
		legend.append(", DeviceModel|MessageId|SerialNumber|UserMessage|UserMessageType|UserMessageModifier, NumeroHilos, NumeroDeProcesosPorHilo");
		legend.append("\n").append(CommandsType.COMMAND6);
		legend.append(", DeviceModel|MessageId|ScriptConf|SerialNumber|ParameterID|ParameterValue, NumeroHilos, NumeroDeProcesosPorHilo");
		legend.append("\n").append(CommandsType.COMMAND7);
		legend.append(", DeviceModel|MessageId|SerialNumber|ParameterID|ParameterValue|estadoInicial , NumeroHilos, NumeroDeProcesosPorHilo");
		legend.append("\n").append(CommandsType.COMMAND7TO3);
		legend.append(", DeviceModel|MessageId|SerialNumber|ParameterID|ParameterValue|estadoInicial , NumeroHilos , NumeroDeProcesosPorHilo");
		legend.append("\n").append(CommandsType.LMSERVER);
		legend.append(", HexadecimalValue|Host, NumeroHilos, NumeroDeProcesosPorHilo");

		Scanner scanner = new Scanner(new InputStreamReader(System.in));

		logger.info(legend.toString());

		String lineCmd = null;

		while (((lineCmd = scanner.nextLine()) != null)) { // /////////////////

			logger.info(legend.toString());

			String[] readLine = lineCmd.split(",");

			String commandMsg = readLine[0].trim();
			String message = readLine[1].trim();
			Long numHilos = Long.parseLong(readLine[2].trim());
			Long nProcess = Long.parseLong(readLine[3].trim());

			ExecutorService executor = Executors.newFixedThreadPool(command.getThreadsPool());

			List<Future<Long>> taskList = new ArrayList<Future<Long>>();
			// Espera a que terminen todos los hilos.
			for (int i = 0; i < numHilos.intValue(); i++) {
				OperationExecuted worker = new OperationExecuted(command, nProcess, commandMsg, message);
				Future<Long> task = executor.submit(worker);
				taskList.add(task);
			}
			for (Future<Long> taskFinished : taskList) {
				long resultado = taskFinished.get();
				logger.info("Task Finished:{}", resultado);
			}
			executor.shutdown();
		}// /////////////////////////////////////////////////////////////////////
		 // command.sendMessage(commandMsg, message);
	}

}