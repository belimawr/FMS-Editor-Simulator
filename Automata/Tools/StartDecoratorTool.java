package Automata.Tools;

import Automata.Decorators.AutomataDecorator;
import Automata.Decorators.StartStateDecorator;
import Automata.Exceptions.Automata_Exception;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.ActionTool;

/**
 * Author: Tiago de França Queiroz
 * Date: 02/01/14
 *
 * Copyright Tiago de França Queiroz, 2014.
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
public class StartDecoratorTool extends ActionTool
{
	public StartDecoratorTool(DrawingView itsView)
	{
		super(itsView);
	}

	/*
	 * If the figure is not decorated,
	 * add an EndDec,
	 * else
	 * peel the decorator off and replace
	 * the figure by itself...
	 */
	@Override
	public void action(Figure figure)
	{
		FSM_Model model = FSM_Model.getInstance();
		FSM_Node node;

		/* Remove the decorator */
		if(figure instanceof StartStateDecorator)
		{
			Figure f = ((StartStateDecorator) figure).peelDecoration();
			drawing().replace(figure, f);

			model.setStart(null);
		}
		/* Do nothing */
		else if(model.getStart() != null)
		{
			return;
		}
		/* Put the decorator */
		else
		{
			Figure start = figure;
			drawing().replace(figure, new StartStateDecorator(figure));

			if(start instanceof AutomataDecorator)
				start = ((AutomataDecorator) start).getParent();
			node = model.getNode(start);
			if(node == null)
				throw new Automata_Exception("Start state is null: "+start);
			model.setStart(node);
		}
	}
}
