package Automata.Tools;

import Automata.Model.FSM_Model;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.ActionTool;

/**
 * Author: Tiago de França Queiroz
 * Date: 04/01/14
 *
 * Copyright Tiago de França Queiroz, 2014.
 *
 * This file is part of Automata.
 *
 * Automata.PACKAGE_NAME is free software: you can redistribute it and/or modify
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
public class InputStringSelector
		extends ActionTool
{
	public InputStringSelector(DrawingView itsView)
	{
		super(itsView);
	}

	@Override
	public void action(Figure figure)
	{
		if(figure instanceof TextFigure)
		{
			FSM_Model model = FSM_Model.getInstance();
			model.setTape_storage((TextFigure) figure);
		}
	}
}
