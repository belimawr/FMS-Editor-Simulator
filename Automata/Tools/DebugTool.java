package Automata.Tools;

import Automata.Decorators.StartStateDecorator;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.figure.DecoratorFigure;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.SelectionTool;

import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 * Author: Tiago de França Queiroz
 * Date: 27/12/13
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
public class DebugTool extends SelectionTool
{
	public DebugTool(DrawingView view)
	{
		super(view);
	}

	@Override
	public void mouseDown(MouseEvent e, int x, int y)
	{
		/* DEBUG MESSAGE */
        System.out.printf("\n");
        FSM_Model.getInstance().print_debug();
		System.out.printf("\n");

		super.mouseDown(e, x, y);

		FSM_Model model = FSM_Model.getInstance();

		Figure f = drawing().findFigure(x, y);

		if(f != null)
		{
			if (f instanceof StartStateDecorator)
			{
				f = ((StartStateDecorator) f).getFigure();
				System.out.print("Start State: ");
			}
			else if(f instanceof DecoratorFigure)
			{
				System.out.printf("This Figure is Decorated\n");
				Enumeration<Figure> figures;

				figures = f.decompose();
				Figure ff;
				while(figures.hasMoreElements())
				{
					ff = figures.nextElement();
					System.out.printf("Decompose: %s\n", ff);
				}

				figures = f.figures();
				while(figures.hasMoreElements())
				{
					ff = figures.nextElement();
					System.out.printf("Figures: %s\n", ff);
				}
			}

			FSM_Node node = model.getNode(f);
			if(node != null)
				System.out.printf("%s is valid? %s\n", node, node.isValid());
			else
				System.out.printf("Figure Selected: %s\n", f);
		}
		else
			System.out.println("Figure is null!");
	}
}
