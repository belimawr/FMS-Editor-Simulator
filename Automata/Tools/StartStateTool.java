package Automata.Tools;

import A_Planet_Simulator.AtmosphereDecorator;
import Automata.Decorators.StartStateDecorator;
import Automata.Figures.CountingFigure;
import Automata.Figures.StartState;
import Automata.Model.FSM_Model;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeListener;
import CH.ifa.draw.tool.ActionTool;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
 * This file is part of Automata.Automata.
 *
 * Automata.Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata.Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata.Automata. If not, see <http://www.gnu.org/licenses/>.
 */
public class StartStateTool extends ActionTool
{
	public StartStateTool(DrawingView itsView)
	{
		super(itsView);
	}

	/*
	 * replaces a CountingFigure by an StartState
	 * or the other way around.
	 *
	 * Also do the necessary modifications on the data
	 * structure.
	 */
	@Override
	public void action(Figure figure)
	{
		FSM_Model model = FSM_Model.getInstance();
		if(figure instanceof CountingFigure)
		{
			FigureChangeListener listener = figure.listener();
			figure.removeFigureChangeListener(listener);
			StartState start = new StartState((CountingFigure) figure);
			start.addFigureChangeListener(listener);
			drawing().replace(figure, start);
			model.replace(figure, start);
		}
	}
}
