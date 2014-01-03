package Automata.Tools;

import Automata.Decorators.AutomataDecorator;
import Automata.Decorators.FinalStateDecorator;
import Automata.Decorators.StartStateDecorator;
import Automata.Figures.CountingFigure;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.ActionTool;

import java.awt.event.MouseEvent;

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
public class FinalStateDecoratorTool
		extends ActionTool
{
	public FinalStateDecoratorTool(DrawingView itsView)
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
		/* Put decorator */
		if(figure instanceof CountingFigure)
		{
			drawing().replace(figure, new FinalStateDecorator(figure));
		}
		else if(figure instanceof AutomataDecorator)
		{
			/* Remove END decorator */
			if(figure instanceof FinalStateDecorator)
			{
				Figure f = ((FinalStateDecorator) figure).peelDecoration();
				drawing().replace(figure, f);
			}
			/* Remove END Decorator */
			else if(((AutomataDecorator) figure).getEndDecorator() != null &&
					((AutomataDecorator) figure).getStartDecorator() != null &&
					figure instanceof StartStateDecorator)
			{
				Figure f = ((StartStateDecorator) figure).peelDecoration();
				while(!(f instanceof CountingFigure))
					f = ((AutomataDecorator) f).peelDecoration();
				StartStateDecorator final_fig = new StartStateDecorator(f);
				drawing().replace(figure, final_fig);
			}
			/* Put decorator in the right order */
			else if(figure instanceof StartStateDecorator)
			{
				Figure f = ((StartStateDecorator) figure).peelDecoration();
				while(!(f instanceof CountingFigure))
					f = ((AutomataDecorator) f).peelDecoration();

				FinalStateDecorator tmp = new FinalStateDecorator(f);
				StartStateDecorator final_dec = new StartStateDecorator(tmp);

				drawing().replace(figure, final_dec);
			}
		}
	}

	/*
	 * Let the tool active
	 */
	@Override
	public void mouseUp(MouseEvent e, int x, int y)
	{
	}
}
