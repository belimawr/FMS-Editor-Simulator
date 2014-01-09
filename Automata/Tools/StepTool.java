package Automata.Tools;

import Automata.Model.FSM_Model;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.ActionTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Author: Tiago de França Queiroz
 * Date: 04/01/14
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
public class StepTool
		extends ActionTool
{
	public StepTool(DrawingView itsView)
	{
		super(itsView);
	}

	@Override
	public void action(Figure figure)
	{
		FSM_Model model = FSM_Model.getInstance();

		if(figure == model.getTape_storage())
		{
			if(model.isValid())
				model.step();
			else
				JOptionPane.showMessageDialog((Component) view(), "FSM is invalid!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void mouseUp(MouseEvent e, int x, int y)
	{
	}
}
