package Automata.Tools;

import Automata.Exceptions.Automata_Exception;
import Automata.Figures.CountingFigure;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.CreationTool;

/**
 * Author: Tiago de França Queiroz
 * Date: 23/12/13
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
public class ZeroAdder extends CreationTool
{
	/*
 * This Tool does not use a prototype as I want
 * to keep track of the number of states created
 * (to label them) and to do so I need a completely
 * new object every time a new figure is created.
 */
	public ZeroAdder(DrawingView view)
	{
		super(view);
	}

	public ZeroAdder(DrawingView view, Figure prototype)
	{
		super(view, prototype);
		throw new Automata_Exception("Prototype cannot be used!");
	}

	@Override
	protected Figure createFigure()
	{
		CountingFigure f = new CountingFigure(false);
		FSM_Node node = new FSM_Node(f);

		FSM_Model model = FSM_Model.getInstance();
		model.insert(f, node);

		System.out.printf("CreatedFigure: %s\n", (f).toString());
		return f;
	}
}
