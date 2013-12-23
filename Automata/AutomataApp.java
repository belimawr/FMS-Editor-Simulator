package Automata;

import Automata.Figures.OneConnection;
import Automata.Figures.ZeroConnection;
import Automata.Tools.OneAdder;
import Automata.Tools.StartStateTool;
import Automata.Tools.ZeroAdder;
import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.palette.ToolButton;
import CH.ifa.draw.tool.ConnectionTool;

import javax.swing.*;

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

		new_tool = new OneAdder(view());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "New One Adder", new_tool));

		new_tool = new ZeroAdder(view());
		palette.add(createToolButton(IMAGES + "ELLIPSE", "New Zero Adder", new_tool));

		new_tool = new StartStateTool(view());
		palette.add(createToolButton(IMAGES + "BORDDEC", "Start State", new_tool));

		new_tool = new ConnectionTool(view(), new OneConnection());
		palette.add(createToolButton(IMAGES + "CONN", "1 Connection", new_tool));

		new_tool = new ConnectionTool(view(), new ZeroConnection());
		palette.add(createToolButton(IMAGES + "CONN", "1 Connection", new_tool));
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
