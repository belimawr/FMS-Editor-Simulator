package Automata.Tools;

import Automata.Figures.CountingFigure;
import Automata.Figures.EndState;
import Automata.Figures.StartState;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeListener;
import CH.ifa.draw.tool.ActionTool;

/**
 * Author: Tiago de França Queiroz
 * Date: 02/01/14
 * <p/>
 * Copyright Tiago de França Queiroz, 2014.
 * <p/>
 * This file is part of Automata.Automata.Tools.
 * <p/>
 * Automata.Automata.Tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * Automata.Automata.Tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata.Automata.Tools. If not, see <http://www.gnu.org/licenses/>.
 */
public class EndStateTool extends ActionTool
{
	public EndStateTool(DrawingView itsView)
	{
		super(itsView);
	}

	@Override
	public void action(Figure figure)
	{
		FSM_Model model = FSM_Model.getInstance();
		if(figure instanceof CountingFigure)
		{
			FigureChangeListener listener = figure.listener();
			figure.removeFigureChangeListener(listener);

			EndState end_state = new EndState((CountingFigure) figure);

			drawing().replace(figure, end_state);
			model.replace(figure, end_state);

			FSM_Node node = model.getNode(end_state);
			node.setEnd(true);

			end_state.addFigureChangeListener(listener);
		}
		else if(figure instanceof EndState)
		{
			CountingFigure old = ((EndState) figure).getCircle();
			FigureChangeListener listener = figure.listener();

			figure.removeFigureChangeListener(listener);

			old.addFigureChangeListener(listener);

			drawing().replace(figure, old);
			model.replace(figure, old);

			FSM_Node node = model.getNode(old);
			node.setEnd(false);
		}
	}
}
