package Automata;

import Automata.Figures.*;
import Automata.Model.FSM_Model;
import Automata.Tools.*;
import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.palette.ToolButton;
import CH.ifa.draw.tool.ConnectedTextTool;
import CH.ifa.draw.tool.TextTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
 * This file is part of Automata.
 *
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */
public class AutomataApp extends DrawApplication
{
	private static final String AUTOMATA_IMAGES = "/Automata/Images/";
	/**
	 * Creates an instance of PlanetSimulatorApp
	 * and starts it
	 * @param args  unused
	 */
	public static void main(String[] args)
	{
		DrawApplication me = new AutomataApp();
		me.open();
	}

	public AutomataApp()
	{
		super("Tiago's FMS editor and simulator");
		TextFigure.setCurrentFontSize(20);
	}

	@Override
	protected Drawing createDrawing()
	{
		Drawing d =  super.createDrawing();

		FSM_Model.getInstance().setDrawing(d);
		return d;
	}

	private JMenu createValidationMenu()
	{
		JMenu menu = new JMenu("Validation");
		JMenuItem mi = new JMenuItem("Validate FSM");
		mi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FSM_Model model = FSM_Model.getInstance();
				String text;
				if(model.isValid())
					text = "This Finite State Machine is valid!";
				else
					text = "This Finite State Machine is invalid!";
				JOptionPane.showMessageDialog((Component) view(), text, "FSM Validation", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menu.add(mi);

		return menu;
	}

	private JMenu createAbout()
	{
		JMenu menu = new JMenu("Help");
		JMenuItem mi = new JMenuItem("About");
		mi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FSM_Model model = FSM_Model.getInstance();
				String text = "";

				text += "Tiago's Finite State Machine Simulator.\n" +
						"This software was developed as a coursework to the subject\n" +
						"CS409: Software Architecture And Design\n" +
						"at University of Strathclyde.\n" +
						"Copyright (C) 2013  Tiago de França Queiroz\n\n";

				text += "This program is free software: you can redistribute it and/or modify\n" +
						"it under the terms of the GNU General Public License as published by\n" +
						"the Free Software Foundation, either version 3 of the License, or\n" +
						"(at your option) any later version.\n\n";

				text += "This program is distributed in the hope that it will be useful,\n" +
						"but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
						"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
						"GNU General Public License for more details.\n\n";

				text += "You should have received a copy of the GNU General Public License\n" +
						"along with this program.  If not, see <http://www.gnu.org/licenses/>.";

				JOptionPane.showMessageDialog((Component) view(), text, "Tiago's FSM Licence",
				                              JOptionPane.PLAIN_MESSAGE);
			}
		});
		menu.add(mi);

		return menu;
	}

	@Override
	protected void createMenus(JMenuBar mb)
	{
		super.createMenus(mb);
		mb.add(createValidationMenu());
		mb.add(createAbout());
	}

	/**
	 * Creates all tools using Prototype
	 *
	 * @param palette the palette where the tools are added.
	 */
	@Override
	protected void createTools(JPanel palette)
	{
		/* Let superclass do its job */
		super.createTools(palette);

		Tool new_tool;

		new_tool = new ConnectedTextTool(view(), new TextFigure());
		palette.add(createToolButton(IMAGES + "ATEXT", "Add text to figure/connection", new_tool));

		new_tool = new TextTool(view(), new TextFigure());
		palette.add(createToolButton(IMAGES + "TEXT", "Text Tool", new_tool));

		new_tool = new StateAdder(view());
		palette.add(createToolButton(AUTOMATA_IMAGES + "ONE", "State", new_tool));

		new_tool = new StartDecoratorTool(view());
		palette.add(createToolButton(AUTOMATA_IMAGES + "START", "Start State", new_tool));

		new_tool = new FinalStateDecoratorTool(view());
		palette.add(createToolButton(AUTOMATA_IMAGES + "END", "End State", new_tool));

		new_tool = new FSM_ConnectionTool(view(), new OneConnection());
		palette.add(createToolButton(AUTOMATA_IMAGES + "1CONN", "One Connection", new_tool));

		new_tool = new FSM_ConnectionTool(view(), new ZeroConnection());
		palette.add(createToolButton(AUTOMATA_IMAGES + "0CONN", "Zero Connection", new_tool));

		new_tool = new InputStringSelector(view());
		palette.add(createToolButton(AUTOMATA_IMAGES + "R", "Set Input String", new_tool));

		new_tool = new StepTool(view());
		palette.add(createToolButton(AUTOMATA_IMAGES + "S", "Read Character", new_tool));

//		new_tool = new DebugTool(view());
//		palette.add(createToolButton(IMAGES + "SEL", "Debug Tool", new_tool));

	}

	/**
	 * Returns a new tool button,
	 * it is just to avoid including "this" as
	 * parameter and make the code a wee bit easier
	 * to understand.
	 *
	 * @param iconName Icon Name
	 * @param toolName Tool Name
	 * @param tool     Tool prototype
	 * @return
	 */
	@Override
	protected ToolButton createToolButton(String iconName, String toolName, Tool tool)
	{
		return new ToolButton(this, iconName, toolName, tool);
	}
}
